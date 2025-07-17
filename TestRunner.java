import java.io.*;
import java.util.*;

public class TestRunner {
  
  private static class TestCase {
    String file1;
    String file2;
    String description;
    ExpectedResult expectedResult;

    TestCase(String file1, String file2, String description, ExpectedResult expectedResult) {
      this.file1 = file1;
      this.file2 = file2;
      this.description = description;
      this.expectedResult = expectedResult;
    }
  }

  private static class ExpectedResult {
    boolean shouldBeIdentical;
    int expectedDifferences;
    List<String> expectedDifferentTypes;

    ExpectedResult(boolean shouldBeIdentical, int expectedDifferences, String... expectedTypes) {
      this.shouldBeIdentical = shouldBeIdentical;
      this.expectedDifferences = expectedDifferences;
      this.expectedDifferentTypes = Arrays.asList(expectedTypes);
    }
  }

  public static void main(String[] args) {
    TestRunner runner = new TestRunner();

    // Generate test data first
    System.out.println("Generating test data...");
    TestDataGenerator generator = new TestDataGenerator();
    generator.generateAllTestFiles();

    // Run all tests
    System.out.println("\nRunning automated tests...\n");
    runner.runAllTests();
  }

  private void runAllTests() {
    List<TestCase> testCases = createTestCases();

    int passed = 0;
    int failed = 0;

    for (int i = 0; i < testCases.size(); i++) {
      TestCase testCase = testCases.get(i);
      System.out.println("=".repeat(60));
      System.out.printf("TEST %d: %s%n", i + 1, testCase.description);
      System.out.println("=".repeat(60));

      boolean result = runSingleTest(testCase);
      if (result) {
        System.out.println("PASSED");
        passed++;
      } else {
        System.out.println("FAILED");
        failed++;
      }
      System.out.println();
    }
    System.out.println("=".repeat(60));
    System.out.println("TEST SUMMARY");
    System.out.println("=".repeat(60));
    System.out.printf("Total Tests: %d%n", testCases.size());
    System.out.printf("Passed: %d%n", passed);
    System.out.printf("Failed: %d%n", failed);
    System.out.printf("Success Rate: %.1f%%%n", (passed * 100.0) / testCases.size());
    System.out.println("=".repeat(60));
  }

  private List<TestCase> createTestCases() {
    List<TestCase> testCases = new ArrayList<>();

    // Test 1: Identical files
    testCases.add(new TestCase(
      "test_identical_1.csv",
      "test_identical_2.csv",
      "Identical files should be detected as identical",
      new ExpectedResult(true, 0)
    ));

    // Test 2: Different cell values
    testCases.add(new TestCase(
      "test_different_cells_1.csv",
      "test_different_cells_2.csv",
      "Different cell values should be detected",
      new ExpectedResult(false, 2, "Cell Value Difference")
    ));

    // Test 3: Different row counts
    testCases.add(new TestCase(
      "test_different_rows_1.csv", 
      "test_different_rows_2.csv",
      "Different row counts should be detected",
      new ExpectedResult(false, 3, "Structural Difference", "Missing Row")
    ));

    // Test 4: Different column counts
    testCases.add(new TestCase(
      "test_different_columns_1.csv", 
      "test_different_columns_2.csv",
      "Different column counts should be detected",
      new ExpectedResult(false, 4, "Structural Difference")
    ));

    // Test 5: Different headers
    testCases.add(new TestCase(
      "test_different_headers_1.csv", 
      "test_different_headers_2.csv",
      "Different headers should be detected",
      new ExpectedResult(false, 2, "Cell Value Difference")
    ));

    // Test 6: Missing rows in middle
    testCases.add(new TestCase(
      "test_missing_middle_1.csv", 
      "test_missing_middle_2.csv",
      "Missing rows in middle should be detected",
      new ExpectedResult(false, 2, "Structural Difference", "Cell Value Difference")
    ));

    // Test 7: Extra columns in some rows
    testCases.add(new TestCase(
      "test_extra_columns_1.csv", 
      "test_extra_columns_2.csv",
      "Extra columns in specific rows should be detected",
      new ExpectedResult(false, 1, "Structural Difference")
    ));

    // Test 8: Empty files
    testCases.add(new TestCase(
      "test_empty_file1.csv", 
      "test_empty_file2.csv",
      "Empty files should be detected as identical",
      new ExpectedResult(true, 0)
    ));

    // Test 9: One empty, one with content
    testCases.add(new TestCase(
      "test_one_empty_1.csv", 
      "test_one_empty_2.csv",
      "One empty file vs one with content should show differences",
      new ExpectedResult(false, 3, "Structural Difference", "Missing Row")
    ));

    // Test 10: Different data types
    testCases.add(new TestCase(
      "test_different_types_1.csv", 
      "test_different_types_2.csv",
      "Different data formats should be detected",
      new ExpectedResult(false, 3, "Cell Value Difference")
    ));

    return testCases;
  }

  private boolean runSingleTest(TestCase testCase) {
    try {
      CSVComparator comparator = new CSVComparator();
      ComparisonResult result = comparator.compareFiles(testCase.file1, testCase.file2);

      if (result == null) {
        System.out.println("ERROR: Failed to compare files");
        return false;
      }

      // Print actual comparison result to debug
      System.out.printf("Comparing: %s vs %s%n", testCase.file1, testCase.file2);
      System.out.printf("Files identical: %b%n", result.isIdentical());
      System.out.printf("Differences found: %d%n", result.getDifferenceCount());
      
      // Check if result matches what's expected
      boolean testPassed = true;

      // Check if identical expectation matches
      if (result.isIdentical() != testCase.expectedResult.shouldBeIdentical) {
        System.out.printf("FAIL: Expected identical=%b, got identical=%b%n", 
            testCase.expectedResult.shouldBeIdentical, result.isIdentical());
        testPassed = false;
      }

      // Check different count
      if (!result.isIdentical()) {
        int actualDifferences = result.getDifferenceCount();
        int expectedDifferences = testCase.expectedResult.expectedDifferences;

        // Non-identical files, mostly only care that difference exist
        if (expectedDifferences > 0 && actualDifferences == 0) {
          System.out.printf("FAIL: Expected %d difference, but found 0%n", expectedDifferences);
          testPassed = false;  
        } else if (expectedDifferences == 0 && actualDifferences > 0) {
          System.out.printf("FAIL: Expected 0 differences, but found %d%n", actualDifferences);
          testPassed = false;
        }

        // Checking if expected difference types are present
        if (testPassed && !testCase.expectedResult.expectedDifferentTypes.isEmpty()) {
          Set<String> actualTypes = new HashSet<>();
          for (Difference diff : result.getDifferences()) {
            actualTypes.add(diff.getType());
        }

        for (String expectedType: testCase.expectedResult.expectedDifferentTypes) {
          if (!actualTypes.contains(expectedType)) {
            System.out.printf("FAIL: Expected difference type '%s', but found none%n", expectedType);
            testPassed = false;
        }
      }
    }
  }

  // Showing actual difference for debugging
  if (!result.isIdentical()) {
    System.out.println("Actual difference found:");
    for (Difference diff : result.getDifferences()) {
      System.out.printf("  - %s: %s%n", diff.getType(), diff.getDescription());
    }
  }

    return testPassed;

    } catch (Exception e) {
      System.out.printf("FAIL: Unexpected exception: %s%n", e.getMessage());
      e.printStackTrace();
      return false;
    }
  }
}