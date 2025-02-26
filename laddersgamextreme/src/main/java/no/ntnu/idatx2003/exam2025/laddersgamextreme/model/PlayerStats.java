package no.ntnu.idatx2003.exam2025.laddersgamextreme.model;

/**
 * A class to produce player statistics based on game events.
 *
 * @author Dennis Moe
 */
public class PlayerStats {
  private int wins;
  private int losses;
  private int laddersUsed;
  // private int spacesMovedWithLadder;
  private int snakesUsed;
  // private int spacesMovedWithSnakes;

  // private int highScore; <- Not useful until "arcade mode"
  // private int eventSpacesUsed;
  // private int cardsUsed;

  public int getWins() {
    return wins;
  }

  public int getLosses() {
    return losses;
  }

  public void addWin() {
    wins++;
  }

  public void addLoss() {
    losses++;
  }

  public int getladdersUsed() {
    return laddersUsed;
  }

  public int getSnakesUsed() {
    return snakesUsed;
  }

  public void incrementLaddersUsed() {
    laddersUsed++;
  }

  public void incrementSnakesUsed() {
    snakesUsed++;
  }
}
