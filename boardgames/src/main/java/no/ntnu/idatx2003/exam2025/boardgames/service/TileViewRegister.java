package no.ntnu.idatx2003.exam2025.boardgames.service;

import java.util.HashMap;
import java.util.Map;

import no.ntnu.idatx2003.exam2025.boardgames.model.tile.*;

/**
 * Uses AI.
 */
public class TileViewRegister {
  private final Map<Class<? extends TileStrategy>, String> registry = new HashMap<>();
  //Specifically AI was used for the Class<?> suggestion.

  public TileViewRegister() {
    register(EmptyTileStrategy.class, "ts-empty");
    register(LadderTileStrategy.class, "ts-ladder");
    register(SnakeTileStrategy.class, "ts-snake");
    register(DoubleMovementTileStrategy.class, "ts-double-movement");
    register(FreezeTileStrategy.class, "ts-freeze");
  }

  public void register(Class<? extends TileStrategy> strategy, String name) {
    registry.put(strategy, name);
  }

  public String get(Class<? extends TileStrategy> strategy) throws Exception {
    String result = registry.get(strategy);
    if (result == null) {
      throw new Exception("No view registered for Tile Strategy: " + strategy);
    }
    return result;
  }
}
