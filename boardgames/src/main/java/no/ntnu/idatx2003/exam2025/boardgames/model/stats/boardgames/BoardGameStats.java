package no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames;

import no.ntnu.idatx2003.exam2025.boardgames.model.stats.PlayerStats;

/**
 * Represents the statistics for a board game, including moves and dice rolls.
 * This interface extends {@link PlayerStats} to include additional board
 * game-specific statistics.
 */
public interface BoardGameStats extends PlayerStats {

  /**
   * Increments the spaces moved (dice roll).
   */
  void incrementMove(int diceRoll);

  /**
   * Retrieves the total number of moves made for the board game.
   *
   * @return the total move count
   */
  int getTotalMoveCount();

  /**
   * Increments the count of dice rolls made in the board game by one.
   * Basically a turn counter
   */
  void incrementDiceRoll();

  /**
   * Retrieves the total number of dice rolls made in the board game.
   *
   * @return the total dice roll count
   */
  int getTotalDiceRolls();
}
