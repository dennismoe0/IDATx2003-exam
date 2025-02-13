package no.ntnu.idatx2003.exam2025.ludo.model;

import javafx.scene.control.Cell;

import java.util.ArrayList;

/**
 * Board contains a list of tiles to be used by BoardView for constructing a game board.
 */
public class Board {
  private ArrayList<Tile> tiles = new ArrayList<>();

  /**
   * Add a game tile to the board.
   * @param tile object of type "tile".
   */
  public void add(Tile tile) {
    tiles.add(tile);
  }

  /**
   * Retrieves an indicated tile.
   * @param pos The position of the tile on the game board (eg, tile #52).
   * @return returns a Tile object.
   */
  public Tile getTileAt(int pos){
    return tiles.get(pos-1); //-1 to account for display showing 1 as the starting point.
  }
}
