import java.util.*;

public class ComparisonResult {
  private String file1Path;
  private String file2Path;
  private boolean identical = false;
  private List<Difference> differences;

  public ComparisonResult(String file1Path, String file2Path) {
    this.file1Path = file1Path;
    this.file2Path = file2Path;
    this.differences = new ArrayList<>();
  }

  public void addDifference(Difference difference) {
    differences.add(difference);
  }


  // Getters and setters
  public String getFile1Path() { return file1Path; }
  public String getFile2Path() { return file2Path; }
  public boolean isIdentical() { return identical; }
  public void setIdentical(boolean identical) { this.identical = identical; }
  public List<Difference> getDifferences() { return differences; }
  public int getDifferenceCount() { return differences.size(); }
}
