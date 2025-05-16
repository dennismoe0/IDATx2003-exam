package no.ntnu.idatx2003.exam2025.boardgames.model.gamepiece;

import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;

/**
 * A movement strategy for freezing the player.
 */
public class FreezeMovementStrategy implements MovementStrategy {
  private int remainingRounds;
  private MovementStrategy initialMovementStrategy;

  /**
   * Default constructor for FreezeMovementStrategy
   *
   * @param remainingRounds and int representing how many rounds a piece will be frozen for.
   * @param initialMovementStrategy the strategy the piece had before being frozen.
   */
  public FreezeMovementStrategy(int remainingRounds, MovementStrategy initialMovementStrategy) {
    this.remainingRounds = remainingRounds;
    this.initialMovementStrategy = initialMovementStrategy;
  }

  /**
   * Movement function for computing how the piece moves.
   *
   * @param roll      the number the player rolled on the dice.
   * @return returns an integer as the number of spaces a player should move.
   */
  @Override
  public int computeMovement(int roll) {
    return 0;
  }

  /**
   * Utility method for movement strategies (in case they're turn based!)
   *
   * @param gamePiece the game piece to be affected, if necessary.
   */
  @Override
  public void onTurnEnd(GamePiece gamePiece) {
    remainingRounds--;
    if (remainingRounds == 0) {

    }
  }

}
