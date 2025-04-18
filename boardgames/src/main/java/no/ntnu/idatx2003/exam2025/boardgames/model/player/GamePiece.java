package no.ntnu.idatx2003.exam2025.boardgames.model.player;

import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;

public class GamePiece {
  private Tile currentTile;

  public void move(int steps){
    for (int i = 0; i < steps; i++) {
      currentTile = currentTile.getNextTile();
    }
  }
}
