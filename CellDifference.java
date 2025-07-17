class CellDifference extends Difference {
  private int row;
  private int column;
  private String value1;
  private String value2;

  public CellDifference(int row, int column, String value1, String value2) {
    super("Cell Value Difference", 
      String.format("Row %d, Column %d: '%s' vs '%s'", row, column, value1, value2));
    this.row =row;
    this.column = column;
    this.value1 = value1;
    this.value2 = value2;
  }

  @Override
  public String getDetailedReport() {
    return String.format("CELL DIFFERENCE:\n" + 
                        " Location: Row %d, Column %d\n" +
                        " File1 Value: '%s'\n" +
                        " File2 Value: '%s'\n",
                        row, column, value1, value2);
  }

  // Getters
  public int getRow() { return row; }
  public int getColumn() { return column; }
  public String getValue1() { return value1; }
  public String getValue2() { return value2; }
}
