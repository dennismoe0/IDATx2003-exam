package no.ntnu.idatx2003.exam2025.boardgames.model.tile;


import java.util.HashMap;
import java.util.Map;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.tilefactory.LadderTileFactory;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.tilefactory.SnakeTileFactory;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.tilefactory.TileFactory;

/**
 * Class used to keep track of what tiles can be used in the program.
 */
public class TileRegistry {
  private final Map<String, TileFactory> registry = new HashMap<>();

  /**
   * Initialization method for a new TileRegistry object
   * immediately initializes known Tile Types.
   */
  public TileRegistry() {
    register("snake", new SnakeTileFactory());
    register("ladder", new LadderTileFactory());
  }

  /**
   * A method for creating a tile factory for the relevant tile type.
   *
   * @param name the tile-type to get a factory for.
   * @return returns a new TileFactory object to corresponding tileType.
   */
  public TileFactory getTileFactory(final String name) {
    return registry.get(name);
  }

  /**
   * Method for registering a new tile type in the TileFactory.
   *
   * @param name    the name of the tile type, as a string. Used as identifier key.
   *                Must be unique.
   * @param factory a corresponding object that implements the TileFactory interface.
   */
  public void register(String name, TileFactory factory) {
    registry.put(name, factory);
  }

}
