package no.ntnu.idatx2003.exam2025.boardgames.model.tile;

import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;
import no.ntnu.idatx2003.exam2025.boardgames.model.gamepiece.FreezeMovementStrategy;

/**
 * A tile that Freezes game pieces for a specified duration.
 */
public class FreezeTileStrategy implements TileStrategy {
  private final int freezeRounds;

  /**
   * Default constructor for a Freeze Tile.
   *
   * @param freezeRounds an integer representing the number of rounds to freeze a piece for.
   */
  public FreezeTileStrategy(int freezeRounds) {
    this.freezeRounds = freezeRounds;
  }

  @Override
  public void applyEffect(GamePiece gamePiece) {
    if (gamePiece.getMovementStrategy() instanceof FreezeMovementStrategy) {
      return;
    } else {
      gamePiece.setMovementStrategy(new FreezeMovementStrategy(freezeRounds));
    }
  }
}
