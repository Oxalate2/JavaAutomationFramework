class MissingRowDifference extends Difference {
  private int rowNumber;
  private String missingFromFile;
  private String rowContent;
  
  public MissingRowDifference(int rowNumber, String missingFromFile, String rowContent) {
    super("Missing Row", 
          String.format("Row %d missing from %s", rowNumber, missingFromFile));
    this.rowNumber = rowNumber;
    this.missingFromFile = missingFromFile;
    this.rowContent = rowContent;
  }

  @Override
  public String getDetailedReport() {
    return String.format("MISSING ROW:\n" +
                        " Row Number: %d\n" +
                        " Missing From: %s\n" +
                        " Content: %s\n",
                        rowNumber, missingFromFile, rowContent);
  }
}
