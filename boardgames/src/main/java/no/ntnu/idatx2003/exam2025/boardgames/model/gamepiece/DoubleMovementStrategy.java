package no.ntnu.idatx2003.exam2025.boardgames.model.gamepiece;

import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;

/**
 * Movement strategy for moving the player twice as far.
 */
public class DoubleMovementStrategy implements MovementStrategy {
  private int duration = 2;

  /**
   * Movement function for computing how the piece moves.
   *
   * @param roll the number the player rolled on the dice.
   * @return returns an integer as the number of spaces the player should move.
   */
  @Override
  public int computeMovement(int roll) {
    return roll * 2;
  }

  /**
   * Utility method for movement strategies (in case they're turn based!).
   *
   * @param gamePiece the game piece to be affected, if necessary.
   */
  @Override
  public void onTurnEnd(GamePiece gamePiece) {
    duration--;
    if (duration == 0) {
      gamePiece.setMovementStrategy(new BasicMovementStrategy());
    }
  }
}
