package no.ntnu.idatx2003.exam2025.boardgames.model.board;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import no.ntnu.idatx2003.exam2025.boardgames.model.tile.*;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.tilefactory.TileFactory;
import no.ntnu.idatx2003.exam2025.boardgames.util.IntPair;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

/**
 * Board Factory class used to build boards for various board games.
 */
public class BoardFactory {
  private static final Logger log = Log.get(BoardFactory.class);
  private final TileRegistry tileRegistry;

  public BoardFactory(){
    tileRegistry = new TileRegistry();
  }

  public BoardFactory (TileRegistry tileRegistry) {
    this.tileRegistry = tileRegistry;
  }

  /**
   * Method for creating the default board game for Snakes n Ladders.
   *
   * @return a finished board object with assigned tiles.
   */
  public Board createDefaultLadderBoard() {
    log.info("Creating default ladder board");
    Board board = new Board();
    Tile tile;
    //initialize the tiles
    //When reading from an XML we'll break the initialize and assemble into separate steps.
    for (int i = 1; i < 91; i++) {
      //switch statement to handle assigning tile functions.
      tile = new Tile(i, new EmptyTileStrategy());
      board.setTile(i, tile);
    }
    // Ensure that each tile has an assigned next tile.
    for (int i = 1; i < 91; i++) {
      if (i + 1 < 91) {
        board.getTile(i).setNextTile(board.getTile(i + 1));
      }

    }

    List<IntPair> ladders = new ArrayList<>(Arrays.asList(
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

    List<IntPair> snakes = new ArrayList<>(Arrays.asList(
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
    log.info("Default ladder board created, {} tiles", board.getBoardSize());
    return board;
  }

  /**
   * A method for Constructing a board from Json objects.
   *
   * @param boardJson the Json extracted from a file representing a board.
   * @return returns a Board object.
   */
  public Board buildBoardFromJson(JsonObject boardJson) {
    JsonArray tiles = boardJson.get("tiles").getAsJsonArray();
    int boardSize = boardJson.get("board-size").getAsInt();
    TileFactory tileFactory;
    Tile tile;
    JsonObject obj;
    String type;


    for (JsonElement tileJson : tiles) {
      obj = tileJson.getAsJsonObject();
      type = obj.get("tile-type").getAsString();
      tileFactory = tileRegistry.getTileFactory(type);
      if (tileFactory == null) {
        throw new IllegalArgumentException("Unknown tile type: " + type);
      }

    }
    return null;
  }


  private Board createBoard(int size) {
    Board board = new Board();
    for (int i = 1; i < size + 1; i++) {
      board.setTile(i, new Tile(i, new EmptyTileStrategy()));
    }
    return board;
  }

}
