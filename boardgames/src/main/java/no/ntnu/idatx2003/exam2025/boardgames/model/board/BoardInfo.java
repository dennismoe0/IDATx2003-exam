package no.ntnu.idatx2003.exam2025.boardgames.model.board;

/**
 * Class use for collecting board information for display in the UI.
 */
public class BoardInfo {
  private String name;
  private String tag;
  private String url;

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getTag() {
    return tag;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }
}
