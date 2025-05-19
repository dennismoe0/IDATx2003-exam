package no.ntnu.idatx2003.exam2025.boardgames.view;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;
import no.ntnu.idatx2003.exam2025.boardgames.service.TileViewRegister;

/**
 * View class for Boards. Uses GridPane as a base.
 */
public class BoardView extends Pane {
  private final StackPane root;
  private final GridPane grid;
  private final Board board;
  private final TileViewRegister tileViewRegister;
  private final float viewWidth = 600;
  private Rectangle backBoard;
  // need to add a way for tracking the movement of game pieces in here, or
  // displaying them in any case.

  /**
   * The default constructor, requires a Board object.
   *
   * @param board the board to be constructed.
   */
  public BoardView(Board board) {
    this.board = board;
    grid = new GridPane();
    root = new StackPane();
    backBoard = new Rectangle();
    root.getChildren().add(backBoard);
    tileViewRegister = new TileViewRegister();
    buildBoardView();
    setConstraints();
    grid.getStyleClass().add("board-view");
    backBoard.getStyleClass().add("board-back-view");
  }

  /**
   * Returns the grid object as a parent. For testing purposes.
   *
   * @return returns a Node object as a parent.
   */
  public Parent asParent() {
    return root;
  }

  private void buildBoardView() {
    List<TileView> tileViews = new ArrayList<TileView>();
    List<Tile> tiles = board.getTilesAsList();
    TileView view;
    String tileType;
    double tileSize = (viewWidth / board.getColumns());

    for (Tile tile : tiles) {
      try {
        tileType = tileViewRegister.get(tile.getTileStrategy().getClass());
      } catch (Exception e) {
        e.printStackTrace();
        tileType = "ts-empty";
      }
      view = new TileView(tile, ((int) tileSize), tileType);
      tileViews.add(view);
      int tileId = tile.getId();
      tileViewRegister.registerTileView(tileId, view);
    }
    assembleBoard(tileViews);
    applyExitTileStyles();
  }

  private void setConstraints() {
    float gridSize = viewWidth - 50;
    grid.setMaxHeight(gridSize);
    grid.setMaxWidth(gridSize);

    backBoard.heightProperty().bind(root.heightProperty());
    backBoard.widthProperty().bind(root.widthProperty());

    root.setMaxHeight(viewWidth);
    root.setMaxWidth(viewWidth);
    root.setPrefSize(viewWidth, viewWidth);
    root.getChildren().add(grid);
    StackPane.setAlignment(grid, Pos.CENTER);
  }

  private void assembleBoard(List<TileView> tileViews) {
    System.out.println("Assembling Board");
    System.out.println("Tile View Size: " + tileViews.size());
    int index = 0;
    int i = 1;
    int j = board.getRows();
    boolean countUp = true;
    while (index < tileViews.size()) {
      grid.add(tileViews.get(index), i, j);
      if (countUp) {
        i++;
      } else {
        i--;
      }
      if (i == board.getColumns() + 1) {
        countUp = false;
        j--;
        i--;
      }
      if (i == 0) {
        countUp = true;
        j--;
        i++;
      }
      index++;
    }
  }

  public TileViewRegister getTileViewRegister() {
    return tileViewRegister;
  }

  public void addGamePieceView(GamePieceView pieceView) {
    root.getChildren().add(pieceView);
  }

  private void applyExitTileStyles() {
    for (Tile tile : board.getTilesAsList()) {
      if (tile.getTileStrategy() != null) {
        // ladder exit
        if (tile.getTileStrategy() instanceof no.ntnu.idatx2003.exam2025.boardgames.model.tile.LadderTileStrategy) {
          no.ntnu.idatx2003.exam2025.boardgames.model.tile.LadderTileStrategy lts = (no.ntnu.idatx2003.exam2025.boardgames.model.tile.LadderTileStrategy) tile
              .getTileStrategy();
          Tile exitTile = lts.getEndTile();
          if (exitTile != null) {
            // Get the TileView for the exit tile from the tileViewRegister
            TileView exitView = tileViewRegister.getTileView(exitTile.getId());
            if (exitView != null) {
              exitView.getView().getStyleClass().add("ts-ladder-end");
            }
          }
        }
        // snake exit
        else if (tile.getTileStrategy() instanceof no.ntnu.idatx2003.exam2025.boardgames.model.tile.SnakeTileStrategy) {
          no.ntnu.idatx2003.exam2025.boardgames.model.tile.SnakeTileStrategy sts = (no.ntnu.idatx2003.exam2025.boardgames.model.tile.SnakeTileStrategy) tile
              .getTileStrategy();
          Tile exitTile = sts.getEndTile();
          if (exitTile != null) {
            TileView exitView = tileViewRegister.getTileView(exitTile.getId());
            if (exitView != null) {
              exitView.getView().getStyleClass().add("ts-snake-end");
            }
          }
        }
      }
    }
  }
}
