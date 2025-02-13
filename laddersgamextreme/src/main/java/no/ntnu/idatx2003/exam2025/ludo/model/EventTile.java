package no.ntnu.idatx2003.exam2025.ludo.model;

public abstract class EventTile extends Tile {
  public EventTile(int x, int y) {
    super(x, y);
  }
  public abstract void event();
}
