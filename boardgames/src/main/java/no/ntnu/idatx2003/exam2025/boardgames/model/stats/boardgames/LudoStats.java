package no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames;

public class LudoStats implements BoardGameStats {
  private int totalDiceRolls;
  private int totalMoveCount;
  private int wins;
  private int losses;
  private int gamesPlayed;
  private int piecesCompleted;
  private int doubleSixRolls;
  private int piecesKnocked;

  @Override
  public void incrementMove(int diceRoll) {
    totalMoveCount += diceRoll;
  }

  @Override
  public int getTotalMoveCount() {
    return totalMoveCount;
  }

  @Override
  public void incrementDiceRoll() {
    totalDiceRolls++;
  }

  @Override
  public int getTotalDiceRolls() {
    return totalDiceRolls;
  }

  public void incrementWins() {
    wins++;
    gamesPlayed++;
  }

  public void incrementLosses() {
    losses++;
    gamesPlayed++;
  }

  public void incrementGamesPlayed() {
    gamesPlayed++;
  }

  public void incrementPiecesCompleted() {
    piecesCompleted++;
  }

  public void incrementDoubleSixRolls() {
    doubleSixRolls++;
  }

  /**
   * Increments the count of pieces "knocked out" during the game.
   */
  public void incrementPiecesKnocked() {
    piecesKnocked++;
  }

  public int getWins() {
    return wins;
  }

  public int getLosses() {
    return losses;
  }

  public int getGamesPlayed() {
    return gamesPlayed;
  }

  public int getPiecesCompleted() {
    return piecesCompleted;
  }

  public int getDoubleSixRolls() {
    return doubleSixRolls;
  }

  public int getPiecesKnocked() {
    return piecesKnocked;
  }

  public void setTotalMoveCount(int value) {
    totalMoveCount = value;
  }

  public void setTotalDiceRolls(int value) {
    totalDiceRolls = value;
  }

  public void setWins(int value) {
    wins = value;
  }

  public void setLosses(int value) {
    losses = value;
  }

  public void setGamesPlayed(int value) {
    gamesPlayed = value;
  }

  public void setPiecesCompleted(int value) {
    piecesCompleted = value;
  }

  public void setDoubleSixRolls(int value) {
    doubleSixRolls = value;
  }

  public void setPiecesKnocked(int value) {
    piecesKnocked = value;
  }
}
