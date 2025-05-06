package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.layout.GridPane;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;

public class BoardView {
  private GridPane grid;
  private Board board;

  public BoardView(Board board) {
    this.board = board;
    grid = new GridPane();
  }

  private void buildBoardView() {

  }
}
