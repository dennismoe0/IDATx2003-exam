package no.ntnu.idatx2003.exam2025.laddersgamextreme.model;

public abstract class Board {

  protected int rows;
  protected int cols;

  public Board(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
  }

  public int getRows() {
    return rows;
  }

  public int getCols() {
    return cols;
  }

  /**
   * Used to ensure the playing piece is not out of bounds.
   * 
   * @param tileNumber The potential tile position.
   * @return True or false depending on the subclass definition of a valid tile.
   */
  public abstract boolean isValidTile(int tileNumber);

  /**
   * Converts the x-y position to a tile number. I.e. row 2 col 4 = 24 (Snakes &
   * Ladders).
   */
  public abstract int convertToTileNumber(int row, int col);

  /**
   * Converts to x-y/row and col.
   * 
   * @param tileNumber the tile to convert.
   * @return An array with the [row, col].
   */
  public abstract int[] convertToRowCol(int tileNumber);

  public abstract 
}
