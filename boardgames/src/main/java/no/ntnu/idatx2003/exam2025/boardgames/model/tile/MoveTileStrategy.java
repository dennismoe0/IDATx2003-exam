package no.ntnu.idatx2003.exam2025.boardgames.model.tile;

import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;

/**
 * An abstract class for teleporting game pieces.
 */
public abstract class MoveTileStrategy implements TileStrategy {
  private final Tile startTile;
  private final Tile endTile;

  /**
   * A constructor for a move tile strategy.
   *
   * @param startTile represents the tile a piece should start on.
   * @param endTile represents the tile a piece should end on.
   */
  public MoveTileStrategy(Tile startTile, Tile endTile) {
    this.startTile = startTile;
    this.endTile = endTile;
  }

  @Override
  public void applyEffect(GamePiece gamePiece) {
    gamePiece.setCurrentTile(endTile);
  }

  public Tile getEndTile() {
    return endTile;
  }

  public Tile getStartTile() {
    return startTile;
  }
}
