package no.ntnu.idatx2003.exam2025.boardgames.model.stats.BoardGames;

/**
 * Represents the statistics for the Ludo board game.
 * Tracks the total number of dice rolls and moves made during the game.
 */
public class LudoStats implements BoardGameStats {
  private int totalDiceRolls;
  private int totalMoveCount;

  @Override
  public int getTotalDiceRolls() {
    return totalDiceRolls;
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
  public void incrementMove() {
    totalMoveCount++;
  }
}
