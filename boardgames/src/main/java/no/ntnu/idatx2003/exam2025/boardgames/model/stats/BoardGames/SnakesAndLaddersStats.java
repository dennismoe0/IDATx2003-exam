package no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames;

public class SnakesAndLaddersStats implements BoardGameStats {
  private int totalDiceRolls;
  private int totalMoveCount;
  private int wins;
  private int losses;
  private int gamesPlayed;
  private int laddersUsed;
  private int snakesUsed;
  private int highestDiceRoll;
  private int sumOfAllDiceRolls;

  @Override
  public void incrementMove() {
    totalMoveCount++;
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
  }

  public void incrementLosses() {
    losses++;
  }

  public void incrementGamesPlayed() {
    gamesPlayed++;
  }

  public void incrementLaddersUsed() {
    laddersUsed++;
  }

  public void incrementSnakesUsed() {
    snakesUsed++;
  }

  public void setHighestDiceRoll(int value) {
    highestDiceRoll = value;
  }

  public void addToSumOfAllDiceRolls(int value) {
    sumOfAllDiceRolls += value;
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

  public int getLaddersUsed() {
    return laddersUsed;
  }

  public int getSnakesUsed() {
    return snakesUsed;
  }

  public int getHighestDiceRoll() {
    return highestDiceRoll;
  }

  public int getSumOfAllDiceRolls() {
    return sumOfAllDiceRolls;
  }

  public void setWins(int v) {
    wins = v;
  }

  public void setLosses(int v) {
    losses = v;
  }

  public void setGamesPlayed(int v) {
    gamesPlayed = v;
  }

  public void setLaddersUsed(int v) {
    laddersUsed = v;
  }

  public void setSnakesUsed(int v) {
    snakesUsed = v;
  }

  public void setTotalMoveCount(int v) {
    totalMoveCount = v;
  }

  public void setTotalDiceRolls(int v) {
    totalDiceRolls = v;
  }

  public void setSumOfAllDiceRolls(int v) {
    sumOfAllDiceRolls = v;
  }
}