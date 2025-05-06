package no.ntnu.idatx2003.exam2025.boardgames.service;

import java.util.HashMap;
import java.util.Map;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.EmptyTileStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.LadderTileStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.SnakeTileStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.TileStrategy;

/**
 * Uses AI.
 */
public class TileViewRegister {
  private Map<Class<? extends TileStrategy>, String> registry = new HashMap<>();
  //Specifically AI was used for the Class<?> suggestion.

  public TileViewRegister() {
    register(EmptyTileStrategy.class, "ts-empty");
    register(LadderTileStrategy.class, "ts-ladder");
    register(SnakeTileStrategy.class, "ts-snake");
  }

  public void register(Class<? extends TileStrategy> strategy, String name) {
    registry.put(strategy, name);
  }

  public String get(Class<? extends TileStrategy> strategy) {
    return registry.get(strategy);
  }
}
