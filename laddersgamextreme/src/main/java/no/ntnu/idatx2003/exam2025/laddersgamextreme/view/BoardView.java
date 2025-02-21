package no.ntnu.idatx2003.exam2025.laddersgamextreme.view;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane; // Gridlike pattern of a boardgame
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color; // Color for tiles etc
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle; // width * height size, rectangle is apparently what you use not Square.
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.BoardModel;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.GameSession;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.PlayingPiece;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.SpecialTile;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.service.SpecialTileManager;

/**
 * Represents the view of the game board in the Ladders Game Extreme.
 * This class is responsible for rendering the game board and player tokens.
 *
 * @author Dennis Moe
 */
public class BoardView extends GridPane {

  private final BoardModel boardModel;
  private final GameSession gameSession; // Holds a list of PlayingPiece objects and info

  // Need to add this as a paramter to the BoardView to not make it static
  private static final int TILE_SIZE = 75; // Size in pixels for each square tile

  /**
   * Constructs a BoardView with the specified BoardModel and GameSession.
   *
   * @param boardModel  the model of the game board
   * @param gameSession the current game session
   */
  public BoardView(BoardModel boardModel, GameSession gameSession) {
    this.boardModel = boardModel;
    this.gameSession = gameSession;
    this.setGridLinesVisible(true); //
    buildBoardUi(); // Init
  }

  public int getTileSize() {
    return TILE_SIZE;
  }

  private void buildBoardUi() {
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

        // Set fill colour, potentially redundant
        // tileRectangle.setFill(Color.LIGHTGRAY);

        int tileNumber = boardModel.convertToTileNumberZigzag(modelRow, col, modelCols);

        SpecialTileManager.SpecialTileRole tileRole = gameSession
            .getSpecialTileManager().getTileRole(tileNumber);

        // IF role isnt NONE
        if (tileRole != SpecialTileManager.SpecialTileRole.NONE) {

          SpecialTile specialTile = gameSession
              .getSpecialTileManager().getSpecialTileForTileNumber(tileNumber);

          // IF it is a special tile
          if (specialTile != null) {

            // Ladders, entry and exit
            if (specialTile.getTileType() == SpecialTile.TileType.LADDER) {
              if (tileRole == SpecialTileManager.SpecialTileRole.ENTRY) {
                tileRectangle.setFill(Color.DARKGREEN); // Sets color dark green
              } else if (tileRole == SpecialTileManager.SpecialTileRole.EXIT) {
                tileRectangle.setFill(Color.LIGHTGREEN); // Sets color light green
              }
            }
            // Snakes, entry and exit
            if (specialTile.getTileType() == SpecialTile.TileType.SNAKE) {
              if (tileRole == SpecialTileManager.SpecialTileRole.ENTRY) {
                tileRectangle.setFill(Color.DARKRED); // Sets color dark red
              } else if (tileRole == SpecialTileManager.SpecialTileRole.EXIT) {
                tileRectangle.setFill(Color.ORANGE); // Sets color dark organge
              }
            }

            // Last tile
            if (specialTile.getTileType() == SpecialTile.TileType.FINISH) {
              tileRectangle.setFill(Color.GOLD); // Gold colour for last tile
            }

            if (specialTile.getTileType() == SpecialTile.TileType.START_TILE) {
              tileRectangle.setFill(Color.LIGHTYELLOW); // Light yellow for starting tile
            }
          }

        } else { // IF role is none
          tileRectangle.setFill(Color.LIGHTBLUE);
        }
        tilePane.getChildren().add(tileRectangle);

        Text tileLabel = new Text(Integer.toString(tileNumber));

        tileLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        tileLabel.setFill(Color.BLACK);

        StackPane.setAlignment(tileLabel, Pos.BOTTOM_RIGHT);

        tileLabel.setTranslateX(-5);
        tileLabel.setTranslateY(-5);

        tilePane.getChildren().add(tileLabel);

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
    if (count == 0) {
      return;
    }

    // Predefined positions for up to 5 tokens (expressed as percentages of the tile
    // dimensions).
    double[][] positions;
    switch (count) {
      case 1 -> positions = new double[][] { { 0.5, 0.5 } };
      case 2 -> positions = new double[][] { { 0.3, 0.5 }, { 0.7, 0.5 } };
      case 3 -> positions = new double[][] { { 0.3, 0.3 }, { 0.7, 0.3 }, { 0.5, 0.7 } };
      case 4 -> positions = new double[][] { { 0.3, 0.3 },
          { 0.7, 0.3 }, { 0.3, 0.7 }, { 0.7, 0.7 } };
      case 5 -> positions = new double[][] { { 0.3, 0.3 },
          { 0.7, 0.3 }, { 0.3, 0.7 }, { 0.7, 0.7 }, { 0.5, 0.5 } };
      default -> positions = new double[][] { { 0.5, 0.5 } };
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

  /**
   * Refreshes the game board UI after possible changes of tiles such as
   * moving/changing a ladder or moving a playing piece.
   */
  public void refreshBoard() {
    buildBoardUi();
  }
}
