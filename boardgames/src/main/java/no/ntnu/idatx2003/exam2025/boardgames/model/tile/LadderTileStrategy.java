package no.ntnu.idatx2003.exam2025.boardgames.model.tile;

/**
 * Class representing a LadderTile Strategy.
 */
public class LadderTileStrategy extends MoveTileStrategy {
  /**
   * Base constructor for ladder tile strategy.
   *
   * @param startTile is a tile object where the player piece should start.
   * @param endTile   is a tile object where the player piece should end.
   */
  public LadderTileStrategy(Tile startTile, Tile endTile) {
    super(startTile, endTile);
    if (!startTileIsEarlier(startTile, endTile)) {
      throw new IllegalArgumentException("StartTile is not earlier than the End Tile");
    }
  }

  private boolean startTileIsEarlier(Tile startTile, Tile endTile) {
    return (endTile.getId() > startTile.getId());
  }
}
