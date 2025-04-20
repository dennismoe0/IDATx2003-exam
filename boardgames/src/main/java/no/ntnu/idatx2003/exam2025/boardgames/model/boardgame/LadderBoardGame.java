package no.ntnu.idatx2003.exam2025.boardgames.model.boardgame;

import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;
import no.ntnu.idatx2003.exam2025.boardgames.model.Die;
import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;

import java.util.ArrayList;
import java.util.List;

public class LadderBoardGame extends BoardGame {
  private int NUMBER_OF_PIECES = 1;
  private int NUMBER_OF_DICE = 1;
  private Dice dice;
  private GamePiece currentGamePiece;

  public LadderBoardGame(Board board, List<Player> players, int boardChoice) {
    setBoard(board);
    setUp(players);
  }

  @Override
  public void setUp(List<Player> players) {
    dice.addDice(new Die(6));

    List<GamePiece> pieces = new ArrayList<GamePiece>();
    for (Player player : players) {
      pieces.clear();
      pieces.add(new GamePiece(null)); // Null as placeholder
      super.addPlayerPieces(player, pieces);
    }
  }

  @Override
  public void takeTurn(Player player) {
    super.getFirstPlayerPiece(player).move(dice.rollAllDiceSum());
  }

  // use observer pattern to track player piece positions and fire a "game over"
  // event.

}
