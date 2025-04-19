package no.ntnu.idatx2003.exam2025.boardgames.model.board;

import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;

import java.util.HashMap;

import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

/**
 * Basic Board class to be used for making Tile Based board games.
 * Holds a hashmap of tile objects by ID.
 */
public class Board{
  private HashMap<Integer, Tile> tiles;
  private static final Logger log = Log.get(Board.class);

  public Board(){
    tiles = new HashMap<>();
    log.debug("Board created");
  }
  public Tile getTile(int id){
    return tiles.get(id);
  }
  public void setTile(int id, Tile tile){
    tiles.put(id, tile);
  }

  public void removeTile(int id){
    tiles.remove(id);
  }

  public int getBoardSize(){
    return tiles.size();
  }

//  public Tile getFirstTile(){
//    return tiles.values().iterator().next();
//  }

//  public Tile getLastTile(){
//
//  }
}
