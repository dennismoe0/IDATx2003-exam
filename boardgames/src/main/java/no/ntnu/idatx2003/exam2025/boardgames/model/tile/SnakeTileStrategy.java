package no.ntnu.idatx2003.exam2025.boardgames.model.tile;

/**
 * An implementation of a Move Tile Strategy.
 */
public class SnakeTileStrategy extends MoveTileStrategy {
  /**
   * Constructor for SnakeTileStrategy.
   *
   * @param startTile represents the tile that a piece should start on.
   * @param endTile   represents the tile that a piece should end on.
   */
  public SnakeTileStrategy(Tile startTile, Tile endTile) {
    super(startTile, endTile);
    if (!endTileIsEarlier(startTile, endTile)) {
      throw new IllegalArgumentException("endTile is not earlier than the startTile");
    }
  }

  private boolean endTileIsEarlier(Tile startTile, Tile endTile) {
    return (endTile.getId() < startTile.getId());
  }
}
