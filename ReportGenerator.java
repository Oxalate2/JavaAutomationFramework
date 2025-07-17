import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportGenerator {
  public void generateReport(ComparisonResult result) {
    System.out.println("==========================================");
    System.out.println("CSV COMPARISON REPORT");
    System.out.println("==========================================");
    System.out.println("File 1: " + result.getFile1Path());
    System.out.println("File 2: " + result.getFile2Path());
    System.out.println("==========================================");

    if (result.isIdentical()) {
      System.out.println("RESULT: FILES ARE IDENTICAL");
      } else {
        System.out.println("RESULT: FILES ARE NOT IDENTICAL");
        System.out.println("Total Difference Found: " + result.getDifferenceCount());
        System.out.println();

        // Group differences by type for better triaging
        Map<String, List<Difference>> groupedDifferences = new HashMap<>();
        for (Difference diff : result.getDifferences()) {
          groupedDifferences.computeIfAbsent(diff.getType(), k -> new ArrayList<>()).add(diff);
        }

        // Report for each type of difference
        for (Map.Entry<String, List<Difference>> entry : groupedDifferences.entrySet()) {
          System.out.println("== " + entry.getKey().toUpperCase() + " ==");
          for (Difference diff : entry.getValue()) {
            System.out.println(diff.getDetailedReport());
          }
        }
      }
      System.out.println("==========================================");
      System.out.println("END OF REPORT");
      System.out.println("==========================================");
  }
}
