package no.ntnu.idatx2003.exam2025.boardgames.model.board;

/**
 * Class use for collecting board information for display in the UI.
 */
public class BoardInfo {
  private String name;
  private String game;
  private String url;

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setGame(String game) {
    this.game = game;
  }

  public String getGame() {
    return game;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }
}
