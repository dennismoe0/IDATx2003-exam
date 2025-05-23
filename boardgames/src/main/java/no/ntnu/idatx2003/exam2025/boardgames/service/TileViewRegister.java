package no.ntnu.idatx2003.exam2025.boardgames.service;

import java.util.HashMap;
import java.util.Map;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.*;
import no.ntnu.idatx2003.exam2025.boardgames.view.TileView;

/**
 * Tile view register class for keeping a register of all tile views, to apply styling.
 * Uses AI suggestsions (see marked spot in code).
 */
public class TileViewRegister {
  private final Map<Class<? extends TileStrategy>, String> registry = new HashMap<>();
  // Specifically AI was used for the Class<?> suggestion.

  private final Map<Integer, TileView> tileViewMap = new HashMap<>();

  /**
   * Constructs a tile view register object.
   */
  public TileViewRegister() {
    register(EmptyTileStrategy.class, "ts-empty");
    register(LadderTileStrategy.class, "ts-ladder");
    register(SnakeTileStrategy.class, "ts-snake");
    register(DoubleMovementTileStrategy.class, "ts-double-movement");
    register(FreezeTileStrategy.class, "ts-freeze");
  }

  /**
   * Method for registering a new Tile View.
   *
   * @param strategy a strategy class
   * @param name a string representing the CSS style class name.
   */
  public void register(Class<? extends TileStrategy> strategy, String name) {
    registry.put(strategy, name);
  }

  /**
   * Method for retreiving an input style class.
   *
   * @param strategy a strategy from a given tile on the board.
   * @return returns a string representing the CSS style class.
   * @throws Exception throws an exception when no class is found.
   */
  public String get(Class<? extends TileStrategy> strategy) throws Exception {
    String result = registry.get(strategy);
    if (result == null) {
      throw new Exception("No view registered for Tile Strategy: " + strategy);
    }
    return result;
  }

  /**
   * Registers a TileView for a specific tile ID.
   *
   * @param tileId   the ID of the tile
   * @param tileView the TileView to register
   */
  public void registerTileView(int tileId, TileView tileView) {
    tileViewMap.put(tileId, tileView);
  }

  /**
   * Retrieves the TileView associated with the specified tile ID.
   *
   * @param tileId the ID of the tile
   * @return the TileView associated with the tile ID, or null if not found
   */
  public TileView getTileView(int tileId) {
    return tileViewMap.get(tileId);
  }
}
