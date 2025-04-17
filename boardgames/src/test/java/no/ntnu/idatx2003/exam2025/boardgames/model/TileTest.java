package no.ntnu.idatx2003.exam2025.boardgames.model;

import no.ntnu.idatx2003.exam2025.boardgames.model.tile.EmptyTileStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.TileStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

  Tile tile = new Tile(5);

  @Test
  void getId() {
    int id = tile.getId();
    assertEquals(5, id);
  }

  @Test
  void setId() {
    tile.setId(2);
    assertNotEquals(5, tile.getId());
    assertEquals(2, tile.getId());

  }

  @Test
  void getTileStrategy() {
    TileStrategy strategy = tile.getTileStrategy();
    assertNull(strategy);
    tile.setTileStrategy(new EmptyTileStrategy());
    strategy = tile.getTileStrategy();
    assertEquals(EmptyTileStrategy.class, strategy.getClass());
  }

  @Test
  void setTileStrategy() {
    TileStrategy strategy = tile.getTileStrategy();
    tile.setTileStrategy(null);
    TileStrategy newStrategy = tile.getTileStrategy();
    assertNull(newStrategy);

    tile.setTileStrategy(new EmptyTileStrategy());
    strategy = tile.getTileStrategy();
    assertEquals(EmptyTileStrategy.class, strategy.getClass());
  }

  @Test
  void applyTileEffect() {
    //must be tested at a later date when tile effects are fully implemented.
  }
}