package no.ntnu.idatx2003.exam2025.game;

public class Player {
  private String name;
  private int score;

  public Player(String name) {
    this.name = name;
    this.score = 0;
  }
  public String getName() {
    return name;
  }
  public int getScore() {
    return score;
  }

}
