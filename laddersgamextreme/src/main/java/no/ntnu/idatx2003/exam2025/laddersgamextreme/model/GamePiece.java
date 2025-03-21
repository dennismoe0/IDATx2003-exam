package no.ntnu.idatx2003.exam2025.laddersgamextreme.model;

public class GamePiece {
  private Tile currentTile;

  public Tile getTile(){
    return currentTile;
  }
  public void setTile(Tile tile){
    currentTile = tile;
  }

  private void performTileAction(){
    currentTile.landingAction(this);
  }
}
