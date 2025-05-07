package no.ntnu.idatx2003.exam2025.boardgames.model.boardgame;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;
import no.ntnu.idatx2003.exam2025.boardgames.model.Die;
import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.util.LadderGameMessage;

/**
 * A Board Game class representing Snakes n Ladders / Stigespillet.
 */
public class LadderBoardGame extends BoardGame {
  private Dice dice;
  private GamePiece currentGamePiece;
  private List<Player> players;
  private Player currentPlayer;
  private int playerIndex;
  private boolean gameIsOver;
  private List<LadderGameMessage> messages;

  /**
   * The default constructor for the Ladder Game class.
   *
   * @param board   a collection of tiles to act as a game board.
   * @param players the players participating in the game, to keep track of turns.
   */
  public LadderBoardGame(Board board, List<Player> players) {
    this.players = new ArrayList<>(players);
    messages = new ArrayList<>();
    super.setBoard(board);
    setUp(players);
  }

  @Override
  public void setUp(List<Player> players) {
    gameIsOver = false;
    dice = new Dice();
    dice.addDice(new Die(6));

    List<GamePiece> pieces = new ArrayList<GamePiece>();
    for (Player player : players) {
      pieces.clear();
      pieces.add(new GamePiece(getBoard().getTile(1))); // Null as placeholder
      super.addPlayerPieces(player, pieces);
    }
    playerIndex = 0;
    currentPlayer = players.get(playerIndex);
  }

  @Override
  public void takeTurn() {
    currentPlayer = getNextPlayer();
    int startTile = super.getFirstPlayerPiece(currentPlayer).getCurrentTile().getId();
    super.getFirstPlayerPiece(currentPlayer).move(dice.rollAllDiceSum());
    if (super.getFirstPlayerPiece(currentPlayer).getCurrentTile() == getBoard().getTile(90)) {
      gameIsOver = true;
    }
  }

  /**
   * A method for getting the player who just took their turn.
   *
   * @return returns a player object.
   */
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  private Player getNextPlayer() {
    return (players.get(++playerIndex));
  }

  public boolean isGameOver() {
    return gameIsOver;
  }


  // use observer pattern to track player piece positions and fire a "game over"
  // event.

}
