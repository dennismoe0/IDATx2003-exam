package no.ntnu.idatx2003.exam2025.laddersgamextreme.model;

public class Tile {
  private final TileAction action;
  private final int tileNumber;

  public Tile(TileAction action, int tileNumber) {
    this.action = action;
    this.tileNumber = tileNumber;
  }

  public void landingAction(GamePiece gamePiece){
    if(action != null){
      action.Action(gamePiece);
    }
  }
  public int getTileNumber() {
    return tileNumber;
  }
}
