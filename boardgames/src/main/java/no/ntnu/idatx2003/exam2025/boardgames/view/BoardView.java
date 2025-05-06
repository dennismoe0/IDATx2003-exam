package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.layout.GridPane;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;
import no.ntnu.idatx2003.exam2025.boardgames.service.TileViewRegister;

import java.util.ArrayList;
import java.util.List;

public class BoardView {
  private GridPane grid;
  private Board board;
  private TileViewRegister tileViewRegister;

  public BoardView(Board board) {
    this.board = board;
    grid = new GridPane();
    tileViewRegister = new TileViewRegister();
    buildBoardView();
  }

  private void buildBoardView() {
    List<TileView> tileViews = new ArrayList<TileView>();
    List<Tile> tiles = board.getTilesAsList();
    TileView view;
    String tileType;

    for (Tile tile : tiles) {
      try {
        tileType = tileViewRegister.get(tile.getTileStrategy().getClass());
      } catch (Exception e) {
        e.printStackTrace();
        tileType = "ts-empty";
      }
      view = new TileView(tile, tile.getId(), tileType);
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
    int index = 0;
    int j = 1;
    while (index < tileViews.size()) {
      for (int i = 0; i < 10; i++) {
        grid.add(tileViews.get(index), i, j);
        index++;
      }
      j++;
      for (int i = 10; i > 0; i--) {
        grid.add(tileViews.get(index), i, j);
        index++;
      }
      j++;
    }
  }
}
