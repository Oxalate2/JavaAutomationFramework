import java.io.*;
import java.util.*;

public class CSVComparator {
  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("Usage: java CSVComparator <file1> <file2>");
      return;
    }

    CSVComparator comparator = new CSVComparator();
    ComparisonResult result = comparator.compareFiles(args[0], args[1]);

    if (result != null) {
    // Generate report
    ReportGenerator reporter = new ReportGenerator();
    reporter.generateReport(result);
    }
  }

  public ComparisonResult compareFiles(String file1Path, String file2Path) {
    try {
      List<String[]> csv1 = readCSV(file1Path);
      List<String[]> csv2 = readCSV(file2Path);

      ComparisonResult result = new ComparisonResult(file1Path, file2Path);

      // Check to see if files look identical first
      if (areFilesIdentical(csv1, csv2)) {
        result.setIdentical(true);
        return result;
      }

      // More detailed comparison
      compareStructure(csv1, csv2, result);
      compareContent(csv1, csv2, result);

      return result;
    } catch (IOException e) {
      System.err.println("Error reading files: " + e.getMessage());
      return null;
    }
  }


  private List<String[]> readCSV(String filePath) throws IOException {
    List<String[]> data = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        // Basic CSV parsing
        String[] values = parseCSVLine(line);
        data.add(values);
      }
    }
    return data;
  }

  private String[] parseCSVLine(String line) {
    List<String> values = new ArrayList<>();
    boolean inQuotes = false;
    StringBuilder current = new StringBuilder();

    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);

      if (c == '"') {
        inQuotes = !inQuotes;
      } else if (c == ',' && !inQuotes) {
        values.add(current.toString().trim());
        current = new StringBuilder();
      } else {
        current.append(c);
      }
    }
    values.add(current.toString().trim());

    return values.toArray(new String[0]);
  }

  private boolean areFilesIdentical(List<String[]> csv1, List<String[]> csv2) {
    if (csv1.size() != csv2.size()) return false;

    for (int i = 0; i < csv1.size(); i++) {
      if (!Arrays.equals(csv1.get(i), csv2.get(i))) {
        return false;
      }
    }
    return true;
  }

  private void compareStructure(List<String[]> csv1, List<String[]> csv2, ComparisonResult result) {
    // Compare num of row counts
    if (csv1.size() != csv2.size()) {
      result.addDifference(new StructuralDifference(
        "Row count mismatch",
        "File1: " + csv1.size() + " rows",
        "File2: " + csv2.size() + " rows"
      ));
    }

    // Compare column counts using first row as a reference
    if (!csv1.isEmpty() && !csv2.isEmpty()) {
      int cols1 = csv1.get(0).length;
      int cols2 = csv2.get(0).length;

      if (cols1 != cols2) {
        result.addDifference(new StructuralDifference(
          "Column count mismatch",
          "File1: " + cols1 + " columns",
          "File2: " + cols2 + " columns"
        ));
      }
    }
  }

  private void compareContent(List<String[]> csv1, List<String[]> csv2, ComparisonResult result) {
    int minRows = Math.min(csv1.size(), csv2.size());

    for (int row = 0; row < minRows; row++) {
      String[] row1 = csv1.get(row);
      String[] row2 = csv2.get(row);

      int minCols = Math.min(row1.length, row2.length);

      for (int col = 0; col < minCols; col++) {
        if (!row1[col].equals(row2[col])) {
          result.addDifference(new CellDifference(
            row + 1, col + 1, // 1-based indexing for user friendly reporting
            row1[col],
            row2[col]
          ));
        }
      }

      // Check for missing/extra colums in this row
      if (row1.length != row2.length) {
        result.addDifference(new StructuralDifference(
          "Column count mismatch in row " + (row + 1),
          "File1: " + row1.length + " columns",
          "File2: " + row2.length + " columns"
        ));
      }
    }

    // Check for missing/extra rows
    if (csv1.size() > minRows) {
      for (int i = minRows; i < csv1.size(); i++) {
        result.addDifference(new MissingRowDifference(i + 1, "File2", Arrays.toString(csv1.get(i))));
      }
    }

    if (csv2.size() > minRows) {
      for (int i = minRows; i < csv2.size(); i++) {
        result.addDifference(new MissingRowDifference(i + 1, "File1", Arrays.toString(csv2.get(i))));
      }
    }
  }
}
