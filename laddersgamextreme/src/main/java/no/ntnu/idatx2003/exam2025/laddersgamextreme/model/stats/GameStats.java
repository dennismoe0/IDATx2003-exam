package no.ntnu.idatx2003.exam2025.laddersgamextreme.model.stats;

/**
 * A superclass to handle common player statistics across different board games.
 * This class provides the foundation for tracking basic game statistics like
 * wins and losses,
 * while game-specific subclasses can track statistics unique to their gameplay.
 *
 * @author Dennis Moe
 */
public abstract class GameStats {
  private int wins;
  private int losses;
  private int gamesPlayed;

  /**
   * Default constructor initializing all statistics to zero.
   */
  public GameStats() {
    this.wins = 0;
    this.losses = 0;
    this.gamesPlayed = 0;
  }

  /**
   * Gets the number of wins.
   *
   * @return Number of wins
   */
  public int getWins() {
    return wins;
  }

  /**
   * Gets the number of losses.
   *
   * @return Number of losses
   */
  public int getLosses() {
    return losses;
  }

  /**
   * Gets the total number of games played.
   *
   * @return Number of games played
   */
  public int getGamesPlayed() {
    return gamesPlayed;
  }

  /**
   * Increments the win counter and the games played counter.
   */
  public void addWin() {
    wins++;
    gamesPlayed++;
  }

  /**
   * Increments the loss counter and the games played counter.
   */
  public void addLoss() {
    losses++;
    gamesPlayed++;
  }

  /**
   * Calculates the win percentage.
   *
   * @return Win percentage as a double between 0.0 and 100.0, or 0.0 if no games
   *         have been played
   */
  public double getWinPercentage() {
    if (gamesPlayed == 0) {
      return 0.0;
    }
    return (double) wins / gamesPlayed * 100.0;
  }

  /**
   * Resets all statistics to zero.
   */
  public void resetStats() {
    wins = 0;
    losses = 0;
    gamesPlayed = 0;
  }

  /**
   * Updates game-specific statistics based on the game event.
   * This method should be implemented by subclasses to handle game-specific
   * events.
   * I.e. landed on WIN-> what should be changed.
   *
   * @param eventType The type of event that occurred
   * @param eventData Additional data related to the event
   */
  public abstract void updateGameSpecificStats(String eventType, Object eventData);
}