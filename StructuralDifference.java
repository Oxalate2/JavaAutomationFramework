class StructuralDifference extends Difference {
  private String file1Info;
  private String file2Info;

  public StructuralDifference(String description, String file1Info, String file2Info) {
    super("Structural Difference", description);
    this.file1Info = file1Info;
    this.file2Info = file2Info;
  }

  @Override
  public String getDetailedReport() {
    return String.format("STRUCTURAL DIFFERENCE:\n" +
                        " Type: %s\n" +
                        " File 1: %s\n" +
                        " File2: %s\n",
                        description, file1Info, file2Info);
  }
}
