abstract class Difference {
  protected String type;
  protected String description;

  public Difference(String type, String description) {
    this.type = type;
    this.description = description;
  }

  public String getType() { return type; }
  public String getDescription() { return description; }

  public abstract String getDetailedReport();
}
