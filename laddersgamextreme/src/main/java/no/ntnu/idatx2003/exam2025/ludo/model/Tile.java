package no.ntnu.idatx2003.exam2025.ludo.model;

public class Tile {
  protected int xpos;
  protected int ypos;

  public Tile(int x, int y) {
    xpos = x;
    ypos = y;
  }
  public int getXpos() { return xpos; }
  public int getYpos() { return ypos; }
}
