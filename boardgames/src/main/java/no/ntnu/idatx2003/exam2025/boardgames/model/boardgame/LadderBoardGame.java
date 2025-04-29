package no.ntnu.idatx2003.exam2025.boardgames.model.boardgame;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;
import no.ntnu.idatx2003.exam2025.boardgames.model.Die;
import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;

/**
 * A Board Game class representing Snakes n Ladders / Stigespillet.
 */
public class LadderBoardGame extends BoardGame {
  private Dice dice;
  private GamePiece currentGamePiece;

  /**
   * The default constructor for the Ladder Game class.
   *
   * @param board   a collection of tiles to act as a game board.
   * @param players the players participating in the game, to keep track of turns.
   */
  public LadderBoardGame(Board board, List<Player> players) {
    super.setBoard(board);
    setUp(players);
  }

  @Override
  public void setUp(List<Player> players) {
    dice = new Dice();
    dice.addDice(new Die(6));

    List<GamePiece> pieces = new ArrayList<GamePiece>();
    for (Player player : players) {
      pieces.clear();
      pieces.add(new GamePiece(getBoard().getTile(1))); // Null as placeholder
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
