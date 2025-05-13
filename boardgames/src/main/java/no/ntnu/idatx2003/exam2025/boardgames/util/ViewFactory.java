package no.ntnu.idatx2003.exam2025.boardgames.util;

import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.BoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderBoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.view.BoardGameView;

public class ViewFactory {

  /**
   * Default method for making a LadderBoardGameView
   *
   * @param boardGame the game to be built.
   * @return returns a BoardGameView object.
   */
  public BoardGameView buildLadderBoardGameView(BoardGame boardGame) {
    return new BoardGameView("LadderBoardGame", (LadderBoardGame) boardGame);
  }
}
