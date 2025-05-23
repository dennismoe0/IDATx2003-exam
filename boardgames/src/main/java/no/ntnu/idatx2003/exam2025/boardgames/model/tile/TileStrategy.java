package no.ntnu.idatx2003.exam2025.boardgames.model.tile;

import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;

/**
 * Interface for building tile strategy.
 */
public interface TileStrategy {
  /**
   * Method for applying effects to a game piece.
   *
   * @param gamePiece the game piece to be affected.
   */
  void applyEffect(GamePiece gamePiece);
}
