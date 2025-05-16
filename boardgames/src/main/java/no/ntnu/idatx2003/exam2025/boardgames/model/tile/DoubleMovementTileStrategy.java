package no.ntnu.idatx2003.exam2025.boardgames.model.tile;

import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;
import no.ntnu.idatx2003.exam2025.boardgames.model.gamepiece.DoubleMovementStrategy;

/**
 * A tile strategy for applying Double Movement to the Player.
 */
public class DoubleMovementTileStrategy implements TileStrategy {

  @Override
  public void applyEffect(GamePiece gamePiece) {
    gamePiece.setMovementStrategy(new DoubleMovementStrategy());
  }
}
