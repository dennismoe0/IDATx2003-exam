package no.ntnu.idatx2003.exam2025.laddersgamextreme.model;

/**
 * Modular and resizable board class.
 *
 * @author Dennis Moe
 */
public class BoardModel {

  public int rows;
  public int cols;

  /**
   * Constructor for a modular/resizable board.
   *
   * @param rows how many rows
   * @param cols how many columns
   */
  public BoardModel(int rows, int cols) {
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
   * Converts a row and column position to a tile number.
   * Adds 1 to make the tilenumber not 0-based. (row 0, col 0 = tile 1)
   * IMPORTANT: Assumes a non zigzag pattern of the board.
   *
   * @param row The row number.
   * @param col The column number.
   * @return The tile number.
   */
  public int convertToTileNumber(int row, int col) {
    return row * col + col + 1;
  }

  /**
   * Converts a row and column to a tile number in a zigzag pattern.
   *
   * @param row       The row number.
   * @param col       The column number.
   * @param totalCols The total number of columns.
   * @return The tile number.
   */
  public int convertToTileNumberZigzag(int row, int col, int totalCols) {

    // Represents the "already counted" tiles.
    // I.e. the base value before adding a number based on lane direction.
    int tileNumberBase = row * totalCols;

    // If the row number is even the direction is left -> right
    if (row % 2 == 0) {
      // Visually tile number 26 would be on index row 2 and index col 5
      // I.e. ( 2 * 10 ) + 5 + 1 = 26
      return tileNumberBase + col + 1;
    } else {
      // odd row -> right to left, mirrored
      // i.e. tile visual number 16: row 1, col 4 (column number is always chosen from
      // left to right)
      // = (1 * 10 = 10) + (10 - 1 - 4 = 5) + 1 = 10 + 5 + 1 = 16

      return tileNumberBase + (totalCols - 1 - col) + 1;
    }
  }

  /**
   * Converts a tile number to its corresponding row and column.
   * The opposite of convertToTileNumber.
   *
   * @param tileNumber The tile number to convert.
   * @return An array where the first element is the row and the second element is
   *         the column.
   */
  public int[] convertToRowCol(int tileNumber) {
    // Since convertToTileNumber adds 1 we have to subtract it here.
    int zeroBased = tileNumber - 1;
    int row = zeroBased / cols;
    int col = zeroBased % cols;
    return new int[] {
        row, col
    };
  }

  /**
   * Converts a tile number to its corresponding row and column in a zigzag
   * pattern.
   *
   * @param tileNumber The tile number to convert.
   * @param totalCols  The total number of columns.
   * @return An array where the first element is the row and the second element is
   *         the column.
   */
  public int[] convertToRowColZigzag(int tileNumber, int totalCols) {
    int zeroBased = tileNumber - 1; // i.e. tile 1 visually = tile 0.

    // i.e. tile 3 => 3 - 1 = 2. => 2 / 10 = 0 (Integer division)
    int row = zeroBased / totalCols;

    // left -> right.
    // I.e. tile 3 => 3 - 1 = 2 => 2 % 10 = 2
    int colIgnore = zeroBased % totalCols;

    // If row is even => No flip. Left -> Right.
    // If row is odd => Flip.
    if (row % 2 == 0) {
      return new int[] {
          row, colIgnore
      };
    } else {
      // Say tile 13. => 13 - 1 = 12 ->
      int flippedCol = (totalCols - 1) - colIgnore;
      return new int[] {
          row, flippedCol
      };
    }
  }

  public int getMaxTile() {
    return rows * cols;
  }
}
