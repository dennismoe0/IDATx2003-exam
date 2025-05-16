package no.ntnu.idatx2003.exam2025.boardgames.model.gamepiece;

import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;

/**
 * Interface for controlling piece movement.
 */
public interface MovementStrategy {

  /**
   * Movement function for computing how the piece moves.
   *
   * @param roll      the number the player rolled on the dice.
   * @param gamePiece the gamepiece to be moved/have it's movement applied.
   * @return returns an integer as the number of spaces the player should move.
   */
  int computeMovement(int roll, GamePiece gamePiece);

  /**
   * Utility method for movement strategies (in case they're turn based!)
   *
   * @param gamePiece the game piece to be affected, if necessary.
   */
  void onTurnEnd(GamePiece gamePiece);
}
