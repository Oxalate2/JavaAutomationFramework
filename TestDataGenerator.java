import java.io.*;

public class TestDataGenerator {
  
  public static void main(String[] args) {
    TestDataGenerator generator = new TestDataGenerator();
    generator.generateAllTestFiles();
  }

  public void generateAllTestFiles() {
    try {
      // TC1: Identical files
      createIdenticalFiles();

      // TC2: Different cell values
      createDifferentCellValues();

      // TC3: Different row counts
      createDifferentRowCounts();

      // TC4: Different column counts
      createDifferentColumnCounts();

      // TC5: Different headers
      createDifferentHeaders();

      // TC6: Missing rows in middle
      createMissingRowsInMiddle();

      // TC7: Extra columns in some rows
      createExtraColumnsInRows();

      // TC8: Empty files
      createEmptyFiles();

      // TC9: One empty, one with content
      createOneEmptyOneWithContent();

      // TC10: Different data types
      createDifferentDataTypes();

      System.out.println("All test data files generated successfully");

    } catch (IOException e) {
      System.err.println("Error generating test files: " + e.getMessage());
    }
  }

  // TC1
  private void createIdenticalFiles() throws IOException {
    String[] content = {
      "ID,Name,Age,City",
      "1,John,25,New York",
      "2,Jane,30,Chicago",
      "3,Bob,35,Los Angeles"
    };

    writeFile("test_identical_1.csv", content);
    writeFile("test_identical_2.csv", content);
  }

  // TC2
  private void createDifferentCellValues() throws IOException {
    String[] file1 = {
      "ID,Name,Age,City",
      "1,John,25,New York",
      "2,Jane,30,Chicago",
      "3,Bob,35,Los Angeles"
    };

    String[] file2 = {
      "ID,Name,Age,City",
      "1,Jim,25,New York",
      "2,Jane,30,Chicago",
      "3,Bob,35,Los Angeles"
    };

    writeFile("test_different_cells_1.csv", file1);
    writeFile("test_different_cells_2.csv", file2);
  }

  // TC3
  private void createDifferentRowCounts() throws IOException {
    String[] file1 = {
      "ID,Name,Age,City",
      "1,John,25,New York",
      "2,Jane,30,Chicago",
      "3,Bob,35,Chicago"
    };

    String[] file2 = {
      "ID,Name,Age,City",
      "1,Jim,25,New York",
      "2,Jane,30,Chicago",
      "3,Bob,35,Chicago",
      "4,Alice,28,Seattle",
      "5,Charlie,32,Denver"
    };

    writeFile("test_different_rows_1.csv", file1);
    writeFile("test_different_rows_2.csv", file2);
  }

  // TC4
  private void createDifferentColumnCounts() throws IOException {
    String[] file1 = {
      "ID,Name,Age,City,Country",
      "1,John,25,New York,USA",
      "2,Jane,30,Chicago,USA",
      "3,Bob,35,Los Angeles,USA"
    };

    String[] file2 = {
      "ID,Name,Age,City,Country",
      "1,John,25,New York,USA",
      "2,Jane,30,Chicago,USA",
      "3,Bob,35,Los Angeles,USA"
    };

    writeFile("test_different_columns_1.csv", file1);
    writeFile("test_different_columns_2.csv", file2);
  }

  // TC5
  private void createDifferentHeaders() throws IOException {
    String[] file1 = {
      "ID,Name,Age,City",
      "1,John,25,New York",
      "2,Jane,30,Chicago",
      "3,Bob,35,Los Angeles"
    };

    String[] file2 = {
      "ID,Full Name,Age,Location",
      "1,John Doe,25,New York",
      "2,Jane Smith,30,Chicago",
      "3,Bob Johnson,35,Los Angeles"
    };

    writeFile("test_different_headers_1.csv", file1);
    writeFile("test_different_headers_2.csv", file2);
  }

  // TC6
  private void createMissingRowsInMiddle() throws IOException {
    String[] file1 = {
      "ID,Name,Age,City",
      "1,John,25,New York",
      "2,Jane,30,Chicago",
      "3,Bob,35,Los Angeles",
      "4,Jim,40,Denver"
    };

    String[] file2 = {
      "ID,Name,Age,City",
      "1,John,25,New York",
      // Missing row: Jane,30
      "3,Bob,35,Los Angeles",
      "4,Jim,40,Denver"
    };

    writeFile("test_missing_middle_1.csv", file1);
    writeFile("test_missing_middle_2.csv", file2);
  }

  // TC7
  private void createExtraColumnsInRows() throws IOException {
    String[] file1 = {
      "ID,Name,Age,City",
      "1,John,25,New York",
      "2,Jane,30,Chicago",
      "3,Bob,35,Los Angeles,"
    };

    String[] file2 = {
      "ID,Name,Age,City",
      "1,John,25,New York",
      "2,Jane,30,Chicago,EXTRA DATA",
      "3,Bob,35,Los Angeles"
    };

    writeFile("test_extra_columns_1.csv", file1);
    writeFile("test_extra_columns_2.csv", file2);
  }

  // TC8
  private void createEmptyFiles() throws IOException {
    writeFile("test_empty_file1.csv", new String[]{});
    writeFile("test_empty_file2.csv", new String[]{});
  }

  // TC9
  private void createOneEmptyOneWithContent() throws IOException {
    String[] content = {
      "ID,Name,Age,City",
      "1,John,25,New York"
    };

    writeFile("test_one_empty_1.csv", new String[]{});
    writeFile("test_one_empty_2.csv", content);
  }

  // TC10
  private void createDifferentDataTypes() throws IOException {
    String[] file1 = {
      "ID,Name,Age,Salary",
      "1,John,25,10000",
      "2,Jane,30,20000",
      "3,Bob,35,30000"
    };

    String[] file2 = {
      "ID,Name,Age,Salary",
      "1,John,25,50000.00", // Diff format
      "2,Jane,30,20000",
      "3,Bob,35,N/A" // Diff value type
    };

    writeFile("test_different_types_1.csv", file1);
    writeFile("test_different_types_2.csv", file2);
  }

  private void writeFile(String filename, String[] lines) throws IOException {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
      for (String line : lines) {
        writer.println(line);
      }
    }
  }
}
