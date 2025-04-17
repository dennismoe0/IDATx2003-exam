package no.ntnu.idatx2003.exam2025.boardgames.model;

import org.slf4j.Logger;

import no.ntnu.idatx2003.exam2025.boardgames.util.Log;

/**
 * Serves as the basic object for building and navigating
 * a Board Game board.
 */
public class Tile {
  private static final Logger log = Log.get(Tile.class);
  private int id;
  private TileStrategy tileStrategy;

  /**
   *
   * @param id Unique identifier used for identification and navigation.
   * @param tileStrategy Used to define different types of tiles.
   */
  public Tile(int id, TileStrategy tileStrategy) {
    this.id = id;
    this.tileStrategy = tileStrategy;
    logCreateNewTile();
  }

  public Tile(int id){
    this.id = id;
    this.tileStrategy = null;
    logCreateNewTile();
  }

  public int getId(){
    return id;
  }
  public void setId(int id){
    this.id = id;
  }
  public TileStrategy getTileStrategy() throws NullPointerException{
    if(tileStrategy == null){
      log.error("Tile {}: Get Tile Strategy, Tile Strategy is Null", this.id);
      throw new NullPointerException();
    }
    return tileStrategy;
  }
  public void setTileStrategy(TileStrategy tileStrategy){
    this.tileStrategy = tileStrategy;
  }

  public void applyTileEffect() throws IllegalStateException {
    log.debug("Tile {}: Applying tile effect", this.id);
    if(tileStrategy == null){
      log.error("Tile {}: Tile strategy is null", this.id);
      throw new IllegalStateException("Tile strategy not set");
    }
    tileStrategy.applyEffect();
  }

  private void logCreateNewTile(){
    log.debug("Tile {}: Creating new tile.", id);
  }

}
