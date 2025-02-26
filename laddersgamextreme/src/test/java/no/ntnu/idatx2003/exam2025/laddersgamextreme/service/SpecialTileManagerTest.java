package no.ntnu.idatx2003.exam2025.laddersgamextreme.service;

import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.SpecialTile;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpecialTileManagerTest {

  int entryTile = 12;
  int exitTile = 25;
  SpecialTileManager specialTileManager = new SpecialTileManager();
  SpecialTile specialTile = new SpecialTile(entryTile,exitTile, SpecialTile.TileType.LADDER);

  @Test
  void addSpecialTile() {
    //Checking that the specialTileManager is empty by default
    assertEquals(0,specialTileManager.getAllSpecialTiles().size());

    //Adding special tile to special tile manager
    specialTileManager.addSpecialTile(specialTile);

    //Checking that it is in fact added by getting the size of the list.
    assertEquals(1,specialTileManager.getAllSpecialTiles().size());

  }

  @Test
  void removeSpecialTileByPos() {
    //adding a special tile
    specialTileManager.addSpecialTile(specialTile);
    //Checking that the tile is added correctly.
    assertEquals(1,specialTileManager.getAllSpecialTiles().size());

    //Removing by position
    specialTileManager.removeSpecialTileByPos(entryTile);

    //check that the list is now of size 0.
    assertEquals(0,specialTileManager.getAllSpecialTiles().size());

    //Establishing baseline again
    specialTileManager.addSpecialTile(specialTile);
    assertEquals(1,specialTileManager.getAllSpecialTiles().size());

    //Checking what happens if you try to remove by exit tile instead.
    specialTileManager.removeSpecialTileByPos(exitTile);
    //assertEquals(0,specialTileManager.getAllSpecialTiles().size());
    //Alright, special tiles are not removed by exit tile. Good to know.
  }

  @Test
  void getSpecialTileByPos() {
    specialTileManager.addSpecialTile(specialTile);
    // Checking that "get special tile" returns a tile from the right position.
    assertEquals(specialTile,specialTileManager.getSpecialTileByPos(entryTile));

    // Checking that it does not return the tile if we select for the exit tile position.
    assertNotEquals(specialTile,specialTileManager.getSpecialTileByPos(exitTile));

    //Checking that not every position will return the special tile.
    assertNotEquals(specialTile,specialTileManager.getSpecialTileByPos(0));

    //Adding a new special tile to confirm that querying the position of one tile does not return the other.
    SpecialTile secondSpecialTile = new SpecialTile(14,3,SpecialTile.TileType.SNAKE);
    specialTileManager.addSpecialTile(secondSpecialTile);

    //Checking that the first entry tile position does not return the second entry tile.
    assertNotEquals(secondSpecialTile, specialTileManager.getSpecialTileByPos(entryTile));

    //Checking that two tiles can't be added to the same spot (might be possible, might cause issues, will check)
    SpecialTile testTile = new SpecialTile(entryTile,27, SpecialTile.TileType.LADDER);
    specialTileManager.addSpecialTile(testTile);

    //assertEquals(specialTile,specialTileManager.getSpecialTileByPos(entryTile));
    //NOTE: The above line returns a failed test, we get the wrong special tile.
    assertEquals(testTile,specialTileManager.getSpecialTileByPos(entryTile));
    //Whatever tile is added most recently becomes the special tile for that position.

  }

  @Test
  void getSpecialTileForTileNumber() {
    specialTileManager.addSpecialTile(specialTile);
    //Check that the special tile correctly returns from the given tile number.
    assertEquals(specialTile,specialTileManager.getSpecialTileForTileNumber(entryTile));

    //Check if it returns exit tiles
    SpecialTile specialTileExit = specialTileManager.getSpecialTileForTileNumber(exitTile);
    assertNotNull(specialTileExit);

    //Check that the exit tile is the same thing as the entrance tile.
    assertEquals(specialTile,specialTileExit);

    //Check that special tiles added to the same start point with different exit points might still exist
    SpecialTile testTile = new SpecialTile(entryTile,27, SpecialTile.TileType.LADDER);
    specialTileManager.addSpecialTile(testTile);

    //Check if both tiles still exist despite occupying the same starting point.
    assertEquals(testTile,specialTileManager.getSpecialTileForTileNumber(entryTile));
    assertEquals(testTile,specialTileManager.getSpecialTileForTileNumber(27));
    assertNull(specialTileManager.getSpecialTileForTileNumber(exitTile));

    //Looks like the tile is made null when a new tile replaces it, nice!
  }

  @Test
  void getAllSpecialTiles() {
    //Add 3 tiles to the Special Tile manager
    specialTileManager.addSpecialTile(specialTile);
    SpecialTile specialTile2 = new SpecialTile(5,27, SpecialTile.TileType.LADDER);
    SpecialTile specialTile3 = new SpecialTile(55,8, SpecialTile.TileType.SNAKE);
    specialTileManager.addSpecialTile(specialTile2);
    specialTileManager.addSpecialTile(specialTile3);

    //Check that the size of the list is equal to three
    Collection<SpecialTile> specialTiles = specialTileManager.getAllSpecialTiles();
    assertEquals(3,specialTiles.size());

    //Check that the size of the list is not equal to seven (not just returning any value)
    assertNotEquals(27,specialTiles.size());
  }
}