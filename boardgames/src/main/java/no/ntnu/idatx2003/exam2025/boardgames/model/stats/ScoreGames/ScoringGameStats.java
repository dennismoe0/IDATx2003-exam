package no.ntnu.idatx2003.exam2025.boardgames.model.stats.ScoreGames;

import no.ntnu.idatx2003.exam2025.boardgames.model.stats.PlayerStats;

/**
 * Interface for games that track scores, highscores, and do not i.e. use moving
 * pieces.
 * This could be quizzes, competitions etc.
 */
public interface ScoringGameStats extends PlayerStats {
  /**
   * Adds the specified amount to the player's score.
   *
   * @param amount the amount to add to the score. Can be positive or negative.
   */
  void addScore(int amount);

  /**
   * Retrieves the current score of the player.
   *
   * @return the player's current score as an integer.
   */
  int getScore();
}
