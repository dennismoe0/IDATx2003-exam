package no.ntnu.idatx2003.exam2025.boardgames.model.tile;

import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

/**
 * Serves as the basic object for building and navigating
 * a Board Game board.
 */
public class Tile {
  private static final Logger log = Log.get(Tile.class);
  private int id;
  private TileStrategy tileStrategy;
  private Tile nextTile;

  /**
   * Default constructor.
   *
   * @param id           Unique identifier used for identification and navigation.
   * @param tileStrategy Used to define different types of tiles.
   */
  public Tile(int id, TileStrategy tileStrategy) {
    this.id = id;
    this.tileStrategy = tileStrategy;
    logCreateNewTile();
    nextTile = null;
  }

  /**
   * Tile constructor for building a tile without a tile strategy.
   *
   * @param id an integer representing the tiles ID and position on the board.
   */
  public Tile(int id) {
    this.id = id;
    this.tileStrategy = null;
    logCreateNewTile();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public TileStrategy getTileStrategy() {
    return tileStrategy;
  }

  public void setNextTile(Tile nextTile) {
    this.nextTile = nextTile;
  }

  public Tile getNextTile() {
    return nextTile;
  }

  public void setTileStrategy(TileStrategy tileStrategy) {
    this.tileStrategy = tileStrategy;
  }

  // public void applyTileEffect() throws IllegalStateException {
  // log.debug("Tile {}: Applying tile effect", this.id);
  // tileStrategy.applyEffect();
  // }

  private void logCreateNewTile() {
    log.debug("Tile {}: Creating new tile.", id);
  }

  @Override
  public String toString() {
    return "Tile{"
        + "id=" + id
        + ", tileStrategy="
        + (tileStrategy != null ? tileStrategy.getClass().getSimpleName() : "null")
        + ", nextTileId=" + (nextTile != null ? nextTile.getId() : "null")
        + '}';
  }
}
