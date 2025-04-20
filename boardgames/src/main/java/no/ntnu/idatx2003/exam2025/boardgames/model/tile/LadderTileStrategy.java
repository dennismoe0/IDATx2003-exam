package no.ntnu.idatx2003.exam2025.boardgames.model.tile;

public class LadderTileStrategy extends MoveTileStrategy{
  public LadderTileStrategy(Tile startTile, Tile endTile) {
    super(startTile, endTile);
    if(!startTileIsEarlier(startTile,endTile)){
      throw new IllegalArgumentException("StartTile is not earlier than the End Tile");
    }
  }

  private boolean startTileIsEarlier(Tile startTile, Tile endTile){
    return (endTile.getId() > startTile.getId());
  }
}
