package no.ntnu.idatx2003.exam2025.boardgames.model.board;

import no.ntnu.idatx2003.exam2025.boardgames.model.tile.EmptyTileStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.TileStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

  private static int TILE_AMOUNT = 12;
  static Board board = new Board();

  @BeforeAll
  static void setUp() {
    Tile tile;
    for (int i = 0; i < TILE_AMOUNT; i++) {
      tile = new Tile(i);
      board.setTile(i, tile);
    }
  }

  @Test
  void getTile() {
    Tile tile;
    tile = board.getTile(1);
    assertNotNull(tile);
    assertEquals(1, tile.getId());
    tile = board.getTile(2);
    assertNotNull(tile);
    assertEquals(2, tile.getId());
  }

  @Test
  void setTile() {
    Tile tile = board.getTile(1);
    TileStrategy tileStrategy = tile.getTileStrategy();
    assertNull(tileStrategy);

    tile = new Tile(1, new EmptyTileStrategy());
    board.setTile(1, tile);
    Tile checkTile = board.getTile(1);
    assertNotNull(checkTile);
    assertEquals(EmptyTileStrategy.class, checkTile.getTileStrategy().getClass());
  }

  @Test
  void removeTile() {
    Tile tile = board.getTile(9);
    assertNotNull(tile);

    board.removeTile(9);
    assertNull(board.getTile(9));
  }

  @Test
  void getBoardSize() {
    assertEquals(TILE_AMOUNT, board.getBoardSize());
  }
}