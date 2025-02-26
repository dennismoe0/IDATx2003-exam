package no.ntnu.idatx2003.exam2025.laddersgamextreme.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.SpecialTile;

/**
 * Manages special tiles in the game.
 * Stores, removes and checks for special tiles.
 *
 * @author Dennis Moe
 */
public class SpecialTileManager {
  private final Map<Integer, SpecialTile> specialTiles = new HashMap<>();

  /**
   * Adds a tile to the map of special tiles by position and type.
   *
   * @param specialTile the special tile to be added to the map
   */
  public void addSpecialTile(SpecialTile specialTile) {
    specialTiles.put(specialTile.getEntryTile(), specialTile);
  }

  /**
   * Removes a specialTile from the map by key (integer position).
   *
   * @param position the position of the special tile to be removed
   */
  public void removeSpecialTileByPos(int position) {
    specialTiles.remove(position);
  }

  /**
   * Checks if a tile is special.
   *
   * @param tileNumber the position of the tile to check
   * @return true if the tile is special, false otherwise
   */
  public boolean isSpecialTile(int tileNumber) {
    return specialTiles.containsKey(tileNumber);
  }

  /**
   * Gets a specialTile object by position.
   * This is only for the Entry point, start of action etc.
   * Other method will get both.
   *
   * @param tileNumber position of tile to get.
   * @return Object of that tile, might not be "special".
   */
  public SpecialTile getSpecialTileByPos(int tileNumber) {
    return specialTiles.get(tileNumber);
  }

  /**
   * Gets a special tile object by tile number.
   * Checks both entry and exit points of the special tiles.
   *
   * @param tileNumber the position of the tile to get
   * @return the special tile object if found, null otherwise
   */
  public SpecialTile getSpecialTileForTileNumber(int tileNumber) {
    // Check for entry
    if (specialTiles.containsKey(tileNumber)) {
      return specialTiles.get(tileNumber);
    }

    // Checks if it's an exit
    for (SpecialTile st : specialTiles.values()) {
      if (st.getExitTile() == tileNumber) {
        return st;
      }
    }
    return null;
  }

  /**
   * Enum representing the role of a special tile.
   * ENTRY for entry point, EXIT for exit point, NONE for no special role.
   */
  public enum SpecialTileRole {
    ENTRY,
    EXIT,
    NONE
  }

  /**
   * A method that checks the role of a tile by position number.
   *
   * @param tileNumber 1-based position.
   * @return SpecialTileRole.ENTRY if entry point, .EXIT if exit point.
   */
  public SpecialTileRole getTileRole(int tileNumber) {
    if (specialTiles.containsKey(tileNumber)) {
      return SpecialTileRole.ENTRY;
    }
    for (SpecialTile st : specialTiles.values()) {
      if (st.getExitTile() == tileNumber) {
        return SpecialTileRole.EXIT;
      }
    }
    return SpecialTileRole.NONE;
  }

  /**
   * Collection of all special tiles for use in Main.
   *
   * @return a collection of all special tiles.
   */
  public Collection<SpecialTile> getAllSpecialTiles() {
    return specialTiles.values();
  }
}
