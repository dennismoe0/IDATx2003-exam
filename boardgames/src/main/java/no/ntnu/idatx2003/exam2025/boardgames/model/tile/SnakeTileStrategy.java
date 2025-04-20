package no.ntnu.idatx2003.exam2025.boardgames.model.tile;

import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;

public class SnakeTileStrategy extends MoveTileStrategy{
  public SnakeTileStrategy(Tile startTile, Tile endTile) {
    super(startTile, endTile);
    if(!endTileIsEarlier(startTile,endTile)){
      throw new IllegalArgumentException("endTile is not earlier than the startTile");
    }
  }

  private boolean endTileIsEarlier(Tile startTile, Tile endTile){
    return (endTile.getId() < startTile.getId());
  }
}
