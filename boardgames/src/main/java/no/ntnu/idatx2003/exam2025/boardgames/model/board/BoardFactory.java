package no.ntnu.idatx2003.exam2025.boardgames.model.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.EmptyTileStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.LadderTileStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.SnakeTileStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;
import no.ntnu.idatx2003.exam2025.boardgames.util.IntPair;

/**
 * Board Factory class used to build boards for various board games.
 */
public class BoardFactory {

  /**
   * Method for creating the default board game for Snakes n Ladders.
   *
   * @return a finished board object with assigned tiles.
   */
  public Board createDefaultLadderBoard() {
    Board board = new Board();
    Tile tile;
    //initialize the tiles
    //When reading from an XML we'll break the initialize and assemble into separate steps.
    for (int i = 1; i < 91; i++) {
      //switch statement to handle assigning tile functions.
      tile = new Tile(i, new EmptyTileStrategy());
      board.setTile(i, tile);
    }
    List<IntPair> ladders = new ArrayList<IntPair>(Arrays.asList(
        new IntPair(1, 40),
        new IntPair(8, 10),
        new IntPair(36, 52),
        new IntPair(43, 52),
        new IntPair(49, 79),
        new IntPair(65, 82),
        new IntPair(68, 85)
    ));
    for (IntPair pair : ladders) {
      Tile startTile = board.getTile(pair.a());
      Tile endTile = board.getTile(pair.b());
      startTile.setTileStrategy(new LadderTileStrategy(startTile, endTile));
    }

    List<IntPair> snakes = new ArrayList<IntPair>(Arrays.asList(
        new IntPair(24, 5),
        new IntPair(33, 3),
        new IntPair(42, 30),
        new IntPair(56, 37),
        new IntPair(64, 27),
        new IntPair(74, 12),
        new IntPair(87, 70)
    ));
    for (IntPair pair : snakes) {
      Tile startTile = board.getTile(pair.a());
      Tile endTile = board.getTile(pair.b());
      startTile.setTileStrategy(new SnakeTileStrategy(startTile, endTile));
    }
    return board;
  }

}
