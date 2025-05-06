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
  }
}
