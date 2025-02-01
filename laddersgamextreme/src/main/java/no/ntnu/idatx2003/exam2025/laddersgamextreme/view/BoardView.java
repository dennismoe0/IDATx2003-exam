package no.ntnu.idatx2003.exam2025.laddersgamextreme.view;

import javafx.scene.layout.GridPane; // Gridlike pattern of a boardgame
import javafx.scene.paint.Color; // Color for tiles etc
import javafx.scene.shape.Rectangle; // width * height size, rectangle is apparently what you use not Square.
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.BoardModel;

public class BoardView extends GridPane {

  private final BoardModel boardModel;
  private static final int TILE_SIZE = 50; // Size in pixels for each square tile

  public BoardView(BoardModel boardModel) {
    this.boardModel = boardModel;
    this.setGridLinesVisible(true); //
    buildBoardUI(); // Init
  }

  private void buildBoardUI() {
    // Resets UI
    this.getChildren().clear();

    int modelRows = boardModel.getRows();
    int modelCols = boardModel.getCols();
    int totalRows = modelRows;

    // Loop over each row and column in BoardModel
    // Until they match
    for (int modelRow = 0; modelRow < modelRows; modelRow++) {
      // Model rows start at the top left, inverts.
      int uiRow = totalRows - 1 - modelRow;

      // Loop until count matches
      for (int col = 0; col < modelCols; col++) {
        // Uses the zigzag pattern for Snakes and Ladders
        // 0-based row and col system
        // Assigns a number to the tile
        int tileNumber = boardModel.convertToTileNumberZigzag(modelRow, col, modelCols);

        // Create a square 50x50px
        Rectangle tileRectangle = new Rectangle(TILE_SIZE, TILE_SIZE);
        tileRectangle.setStroke(Color.BLACK); // Black border

        // Set fill colour
        tileRectangle.setFill(Color.LIGHTGRAY);

        /*
         * This probably needs to get its own class.
         * For better handling, assigning etc properties to tiles.
         * Make it interact properly with player position/player playing piece.
         */
        String tileType = boardModel.getTileType(tileNumber);
        if (tileType != null) {
          switch (tileType) {
            case "START_TILE" -> tileRectangle.setFill(Color.LIGHTYELLOW);
            case "SNAKE_START" -> tileRectangle.setFill(Color.RED);
            case "LADDER_START" -> tileRectangle.setFill(Color.DARKGREEN);
            case "FINISH" -> tileRectangle.setFill(Color.GOLD);
            default -> tileRectangle.setFill(Color.LIGHTBLUE);
          }
        }
        // Add the rectangle to the GridPane
        // GridPane.add
        // Adds the instance of the rectangle being made to a specific col and row
        // Remember: uiRow exists to "flip" the board so it goes bottom up instead.
        this.add(tileRectangle, col, uiRow);
      }
    }
  }

  // Refresh after possible changes of tiles such as moving/changing a ladder.
  public void refreshBoard() {
    buildBoardUI();
  }
}
