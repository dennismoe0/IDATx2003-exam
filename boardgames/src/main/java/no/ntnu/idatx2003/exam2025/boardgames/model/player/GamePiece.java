package no.ntnu.idatx2003.exam2025.boardgames.model.player;

import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;

/**
 * Basic piece that can move around the game board.
 */
public class GamePiece {
  private Tile currentTile;

  /**
   * A function for moving the game piece.
   * @param steps An integer getting the number of spaces the piece will move.
   */
  public void move(int steps){
    for (int i = 0; i < steps; i++) {
      if(currentTile.getNextTile() != null){
        currentTile = currentTile.getNextTile();
      }
      else{break;}
    }
    currentTile.getTileStrategy().applyEffect(this);
  }
}
