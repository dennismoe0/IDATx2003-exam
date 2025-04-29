package no.ntnu.idatx2003.exam2025.boardgames.model.board;


import java.util.HashMap;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

/**
 * Basic Board class to be used for making Tile Based board games.
 * Holds a hashmap of tile objects by ID.
 */
public class Board {
  private HashMap<Integer, Tile> tiles;
  private static final Logger log = Log.get(Board.class);

  /**
   * Default constructor, initializes tile map.
   */
  public Board() {
    tiles = new HashMap<>();
    log.debug("Board created");
  }

  /**
   * Allows a function to receive a specified tile.
   *
   * @param id represents the number of the tile you want to get
   * @return a tile. Can be null.
   */
  public Tile getTile(int id) {
    return tiles.get(id);
  }

  /**
   * Allows a method to replace a specified tile, add a new one.
   *
   * @param id   an integer used to track the tile number.
   * @param tile a tile to be tied to an integer id.
   */
  public void setTile(int id, Tile tile) {
    tiles.put(id, tile);
  }

  /**
   * Remove a specified tile from the board.
   *
   * @param id an integer representing the id of the tile to be removed.
   */
  public void removeTile(int id) {
    tiles.remove(id);
  }

  /**
   * A function used to find the total number of tiles in the board.
   *
   * @return an int representing the number of tiles in the board.
   */
  public int getBoardSize() {
    return tiles.size();
  }
}
