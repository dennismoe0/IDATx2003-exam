package no.ntnu.idatx2003.exam2025.laddersgamextreme.model.stats;

/**
 * Statistics for the Snakes and Ladders game.
 * Extends the GameStats superclass to inherit common statistics functionality.
 *
 * @author Dennis Moe
 */
public class SnakesStats extends GameStats {
  // Snakes and Ladders specific statistics
  private int laddersUsed;
  private int snakesUsed;
  private int highestDiceRoll;
  private int totalDiceRolls;
  private int sumOfAllDiceRolls;

  /**
   * Default constructor initializing all statistics to zero.
   */
  public SnakesStats() {
    super(); // Initialize common statistics
    this.laddersUsed = 0;
    this.snakesUsed = 0;
    this.highestDiceRoll = 0;
    this.totalDiceRolls = 0;
    this.sumOfAllDiceRolls = 0;
  }

  /**
   * Gets the number of ladders used.
   *
   * @return Number of ladders used
   */
  public int getLaddersUsed() {
    return laddersUsed;
  }

  /**
   * Gets the number of snakes used.
   *
   * @return Number of snakes used
   */
  public int getSnakesUsed() {
    return snakesUsed;
  }

  /**
   * Gets the highest dice roll.
   *
   * @return Highest dice roll
   */
  public int getHighestDiceRoll() {
    return highestDiceRoll;
  }

  /**
   * Gets the total number of dice rolls.
   *
   * @return Total number of dice rolls
   */
  public int getTotalDiceRolls() {
    return totalDiceRolls;
  }

  /**
   * Gets the sum of all dice rolls.
   *
   * @return Sum of all dice rolls
   */
  public int getSumOfAllDiceRolls() {
    return sumOfAllDiceRolls;
  }

  /**
   * Gets the average dice roll.
   *
   * @return Average dice roll or 0 if no dice have been rolled
   */
  public double getAverageDiceRoll() {
    if (totalDiceRolls == 0) {
      return 0.0;
    }
    return (double) sumOfAllDiceRolls / totalDiceRolls;
  }

  /**
   * Increments the ladders used counter.
   */
  public void incrementLaddersUsed() {
    laddersUsed++;
  }

  /**
   * Increments the snakes used counter.
   */
  public void incrementSnakesUsed() {
    snakesUsed++;
  }

  /**
   * Records a dice roll.
   *
   * @param value The value of the dice roll
   */
  public void recordDiceRoll(int value) {
    totalDiceRolls++;
    sumOfAllDiceRolls += value;
    if (value > highestDiceRoll) {
      highestDiceRoll = value;
    }
  }

  /**
   * Updates game-specific statistics based on the game event.
   *
   * @param eventType The type of event (e.g., "LADDER", "SNAKE", "DICE_ROLL")
   * @param eventData Additional data related to the event
   */
  @Override
  public void updateGameSpecificStats(String eventType, Object eventData) {
    if (eventType == null) {
      return;
    }

    switch (eventType.toUpperCase()) {
      case "LADDER":
        incrementLaddersUsed();
        break;
      case "SNAKE":
        incrementSnakesUsed();
        break;
      case "DICE_ROLL":
        if (eventData instanceof Integer) {
          recordDiceRoll((Integer) eventData);
        }
        break;
      default:
        // Unknown event type, ignore
        break;
    }
  }

  /**
   * Resets all statistics to zero.
   */
  @Override
  public void resetStats() {
    super.resetStats();
    this.laddersUsed = 0;
    this.snakesUsed = 0;
    this.highestDiceRoll = 0;
    this.totalDiceRolls = 0;
    this.sumOfAllDiceRolls = 0;
  }
}