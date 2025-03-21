package no.ntnu.idatx2003.exam2025.laddersgamextreme.model;

import java.util.ArrayList;

public class Board {
  ArrayList<Tile> tiles = new ArrayList<Tile>();

  public ArrayList<Tile> getTiles() {
    return tiles;
  }
  public Tile getTile(int position) {
    return tiles.get(position);
  }

}
