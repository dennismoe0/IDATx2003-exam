package no.ntnu.idatx2003.exam2025.boardgames.model.tile;

import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;

public class MoveTileStrategy implements TileStrategy {
  private Tile startTile;
  private Tile endTile;

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
