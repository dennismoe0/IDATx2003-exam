package no.ntnu.idatx2003.exam2025.boardgames.model.gamepiece;

import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;

/**
 * The default movement strategy for the game.
 */
public class BasicMovementStrategy implements MovementStrategy {

  /**
   * Movement function for computing how the piece moves.
   *
   * @param roll      the number the player rolled on the dice.
   * @return returns an integer as the number of spaces the player should move.
   */
  @Override
  public int computeMovement(int roll) {
    return roll;
  }

  /**
   * Utility method for movement strategies (in case they're turn based!).
   *
   * @param gamePiece the game piece to be affected, if necessary.
   */
  @Override
  public void onTurnEnd(GamePiece gamePiece) {

  }
}
