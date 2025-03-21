package no.ntnu.idatx2003.exam2025.laddersgamextreme.model;

public class Tile {
  private TileAction action;
  private int tileNumber;

  public void landingAction(GamePiece gamePiece){
    if(action != null){
      action.Action(gamePiece);
    }
  }
  public int getTileNumber() {
    return tileNumber;
  }
}
