package no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames;

import java.util.List;

/**
 * Statistics tracker for the Ludo board game, implementing BoardGameStats.
 * Tracks wins, losses, games played, pieces completed, double six rolls, pieces
 * knocked, total moves, and dice rolls.
 */
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
  public List<String> getStatNames() {
    return List.of(
        "wins",
        "losses",
        "gamesPlayed",
        "piecesCompleted",
        "doubleSixRolls",
        "piecesKnocked",
        "totalMoves",
        "totalDiceRolls");
  }

  @Override
  public List<Integer> getStatValues() {
    return List.of(
        wins,
        losses,
        gamesPlayed,
        piecesCompleted,
        doubleSixRolls,
        piecesKnocked,
        totalMoveCount,
        totalDiceRolls);
  }

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

  /**
   * Increments the win count and the total number of games played.
   */
  public void incrementWins() {
    wins++;
    gamesPlayed++;
  }

  /**
   * Increments the loss count and the total number of games played.
   */
  public void incrementLosses() {
    losses++;
    gamesPlayed++;
  }

  /**
   * Increments the total number of games played.
   */
  public void incrementGamesPlayed() {
    gamesPlayed++;
  }

  /**
   * Increments the count of pieces completed during the game.
   */
  public void incrementPiecesCompleted() {
    piecesCompleted++;
  }

  /**
   * Increments the count of double six rolls during the game.
   */
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
