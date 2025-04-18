package no.ntnu.idatx2003.exam2025.boardgames.model.board;

import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;

import java.util.HashMap;

public class Board{
  private HashMap<Integer, Tile> tiles;
  public Board(){
    tiles = new HashMap<>();
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
}
