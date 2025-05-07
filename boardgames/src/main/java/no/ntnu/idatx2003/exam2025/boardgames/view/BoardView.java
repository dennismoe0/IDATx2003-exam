package no.ntnu.idatx2003.exam2025.boardgames.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;
import no.ntnu.idatx2003.exam2025.boardgames.service.TileViewRegister;

/**
 * View class for Boards. Uses GridPane as a base.
 */
public class BoardView {
  private final GridPane grid;
  private final Board board;
  private final TileViewRegister tileViewRegister;

  /**
   * The default constructor, requires a Board object.
   *
   * @param board the board to be constructed.
   */
  public BoardView(Board board) {
    this.board = board;
    grid = new GridPane();
    tileViewRegister = new TileViewRegister();
    buildBoardView();
  }

  /**
   * Returns the grid object as a parent. For testing purposes.
   *
   * @return returns a Node object as a parent.
   */
  public Parent asParent() {
    return grid;
  }

  private void buildBoardView() {
    List<TileView> tileViews = new ArrayList<TileView>();
    List<Tile> tiles = board.getTilesAsList();
    TileView view;
    String tileType;
    double tileSize = 600.0 / board.getColumns();

    for (Tile tile : tiles) {
      try {
        tileType = tileViewRegister.get(tile.getTileStrategy().getClass());
      } catch (Exception e) {
        e.printStackTrace();
        tileType = "ts-empty";
      }
      view = new TileView(tile, ((int) tileSize), tileType);
      tileViews.add(view);
    }
    assembleBoard(tileViews);
  }

  private void setConstraints() {
    grid.setPrefSize(600, 600);
    grid.setHgap(5);
    grid.setVgap(5);
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
}
