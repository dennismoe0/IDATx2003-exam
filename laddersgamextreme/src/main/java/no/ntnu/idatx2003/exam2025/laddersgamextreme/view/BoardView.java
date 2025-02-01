package no.ntnu.idatx2003.exam2025.laddersgamextreme.view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.GridPane; // Gridlike pattern of a boardgame
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color; // Color for tiles etc
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle; // width * height size, rectangle is apparently what you use not Square.
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.BoardModel;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.GameSession;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.PlayingPiece;

public class BoardView extends GridPane {

  private final BoardModel boardModel;
  private final GameSession gameSession; // Holds a list of PlayingPiece objects and info

  private static final int TILE_SIZE = 50; // Size in pixels for each square tile

  public BoardView(BoardModel boardModel, GameSession gameSession) {
    this.boardModel = boardModel;
    this.gameSession = gameSession;
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

        StackPane tilePane = new StackPane();
        tilePane.setPrefSize(TILE_SIZE, TILE_SIZE);

        // Create a square 50x50px
        Rectangle tileRectangle = new Rectangle(TILE_SIZE, TILE_SIZE);
        tileRectangle.setStroke(Color.BLACK); // Black border

        // Set fill colour
        tileRectangle.setFill(Color.LIGHTGRAY);

        int tileNumber = boardModel.convertToTileNumberZigzag(modelRow, col, modelCols);

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
            case "SNAKE_EXIT" -> tileRectangle.setFill(Color.PINK);
            case "LADDER_START" -> tileRectangle.setFill(Color.DARKGREEN);
            case "LADDER_EXIT" -> tileRectangle.setFill(Color.LIGHTGREEN);
            case "FINISH" -> tileRectangle.setFill(Color.GOLD);
            default -> tileRectangle.setFill(Color.LIGHTBLUE);
          }
        }
        tilePane.getChildren().add(tileRectangle);

        addPlayerTokenToTile(tilePane, tileNumber);

        // Add the rectangle to the GridPane
        // GridPane.add
        // Adds the instance of the rectangle being made to a specific col and row
        // Remember: uiRow exists to "flip" the board so it goes bottom up instead.
        this.add(tilePane, col, uiRow);
      }
    }
  }

  /**
   * Checks for any PlayingPiece objects on the given tile (by tile number) and
   * adds them to the tile's StackPane.
   * Tokens are scaled down and arranged in predefined positions so that they do
   * not overlap.
   *
   * @param tilePane   the StackPane representing the tile
   * @param tileNumber the 1-based tile number
   */
  private void addPlayerTokenToTile(StackPane tilePane, int tileNumber) {
    // Gather all tokens (PlayingPiece objects) that are on this tile.
    List<PlayingPiece> tokens = new ArrayList<>();
    for (PlayingPiece piece : gameSession.getPlayerPieces()) {
      if (piece.getCurrentTile() == tileNumber) {
        tokens.add(piece);
      }
    }
    int count = tokens.size();
    if (count == 0)
      return;

    // Predefined positions for up to 5 tokens (expressed as percentages of the tile
    // dimensions).
    double[][] positions;
    switch (count) {
      case 1 -> positions = new double[][] { { 0.5, 0.5 } };
      case 2 -> positions = new double[][] { { 0.3, 0.5 }, { 0.7, 0.5 } };
      case 3 -> positions = new double[][] { { 0.3, 0.3 }, { 0.7, 0.3 }, { 0.5, 0.7 } };
      case 4 -> positions = new double[][] { { 0.3, 0.3 }, { 0.7, 0.3 }, { 0.3, 0.7 }, { 0.7, 0.7 } };
      case 5 -> positions = new double[][] { { 0.3, 0.3 }, { 0.7, 0.3 }, { 0.3, 0.7 }, { 0.7, 0.7 }, { 0.5, 0.5 } };
      default -> positions = new double[][] { { 0.5, 0.5 } }; // Fallback for more than 5 tokens.
    }

    // Determine a token radius that scales with the number of tokens.
    double tokenRadius = Math.max(10 - (count - 1), 5);

    // For each token, create a Circle, set its color, and position it based on the
    // predefined positions.
    for (int i = 0; i < count; i++) {
      PlayingPiece piece = tokens.get(i);
      Circle token = new Circle(tokenRadius);
      token.setFill(piece.getPieceColor());

      // Calculate pixel offsets from the top-left of the tile.

      double x = positions[i][0] * TILE_SIZE;
      double y = positions[i][1] * TILE_SIZE;

      // StackPane centers its children by default offsets them so that the token is
      // positioned correctly.

      token.setTranslateX(x - TILE_SIZE / 2);
      token.setTranslateY(y - TILE_SIZE / 2);
      tilePane.getChildren().add(token);
    }
  }

  // Refresh after possible changes of tiles such as moving/changing a ladder.
  public void refreshBoard() {
    buildBoardUI();
  }
}
