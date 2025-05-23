package no.ntnu.idatx2003.exam2025.boardgames.model.boardgame;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatx2003.exam2025.boardgames.exception.InvalidDiceAmountException;
import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;
import no.ntnu.idatx2003.exam2025.boardgames.model.Die;
import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.SnakesAndLaddersStats;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;
import no.ntnu.idatx2003.exam2025.boardgames.service.AudioManager;
import no.ntnu.idatx2003.exam2025.boardgames.util.LadderGameMessage;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

/**
 * A Board Game class representing Snakes n Ladders / Stigespillet.
 */
public final class LadderBoardGame extends DiceBoardGame {
  private Dice dice;
  private int playerIndex;
  private final SnakesAndLaddersStats stats;
  private final LadderGameMoveHistory moveHistory = new LadderGameMoveHistory();
  private static final Logger log = Log.get(LadderBoardGame.class);
  private final int lastTile;
  private final List<Player> turnOrder;
  private final AudioManager audioManager;

  /**
   * The default constructor for the Ladder Game class.
   *
   * @param board   a collection of tiles to act as a game board.
   * @param players the players participating in the game, to keep track of turns.
   */
  public LadderBoardGame(int dice, Board board, List<Player> players)
      throws InvalidDiceAmountException {
    super.setPlayers(players);
    this.turnOrder = new ArrayList<>(players);
    this.stats = new SnakesAndLaddersStats(); // Fixed stat connection
    super.setBoard(board); // Board setup is loaded
    this.audioManager = new AudioManager();
    lastTile = board.getBoardSize();
    if (dice <= 0) {
      throw new InvalidDiceAmountException("Must have at least 1 Die to play.");
    }
    if (dice >= 5) {
      throw new InvalidDiceAmountException("Too many dice to play. Limit: 5");
    }

    setUp(players, dice); // Gamepieces are made
  }

  public SnakesAndLaddersStats getSnakesAndLaddersStats() {
    return stats;
  }

  public LadderGameMoveHistory getMoveHistory() {
    return moveHistory;
  }

  private void setUp(List<Player> players, int diceTotal) {
    dice = new Dice();
    for (int i = 0; i < diceTotal; i++) {
      dice.addDice(new Die(6));
    }
    super.setDice(dice);

    for (Player player : players) {
      if (!(player.getPlayerStats() instanceof SnakesAndLaddersStats)) {
        log.debug(
            "Wrong stats for player {}, it is currently: {}",
            player.getPlayerId(), player.getPlayerStats());
        player.setPlayerStats(new SnakesAndLaddersStats());
        log.debug("Created new snakes and ladders stats for player {} since it was wrong.", player);
      }
      List<GamePiece> pieces = new ArrayList<>();
      Tile startingTile = getBoard().getTile(1);
      if (startingTile == null) {
        throw new IllegalArgumentException("Starting tile (tile 1) does not exist.");
      }
      pieces.add(new GamePiece(startingTile, player));
      super.addPlayerPieces(player, pieces);
    }
    playerIndex = 0;
    // currentPlayer = players.get(playerIndex);
  }

  @Override
  public void takeTurn() {
    // Get the current player for this turn
    currentPlayer = getNextPlayer();
    log.info("Starting turn for player index: {}, player: {}",
        playerIndex, currentPlayer.getPlayerName());

    // Roll the dice once
    int diceRoll = dice.rollAllDiceSum();

    // Move the current player's game piece
    GamePiece playerPiece = super.getFirstPlayerPiece(currentPlayer);
    if (playerPiece == null) {
      throw new IllegalStateException("No game piece found for player: "
          + currentPlayer.getPlayerName());
    }
    int startTile;
    try {
      startTile = playerPiece.getCurrentTile().getId();
    } catch (NullPointerException e) {
      startTile = 0;
    }

    playerPiece.move(diceRoll);
    audioManager.playDiceRollSound();

    int endTile = playerPiece.getCurrentTile().getId();
    moveHistory.addMessage(new LadderGameMessage(currentPlayer, startTile, endTile, diceRoll));

    // Log the player's move
    log.info("Player {} moved. Current tile: {}",
        currentPlayer.getPlayerName(), playerPiece.getCurrentTile());

    // Update stats for the current player
    if (!(currentPlayer.getPlayerStats() instanceof SnakesAndLaddersStats playerStats)) {
      throw new IllegalStateException("PlayerStats is not an instance of SnakesAndLaddersStats");
    }

    // Update dice roll and movement stats
    // dont have time to make a listener
    playerStats.incrementDiceRoll();
    log.info("Incremented diceRoll/steps for player {}: {}.", currentPlayer.getPlayerId(),
        currentPlayer.getPlayerName());
    playerStats.incrementMove(diceRoll);
    log.info("Incremented movecount for player {}: {}.",
        currentPlayer.getPlayerId(), currentPlayer.getPlayerName());

    // Check if the player has won
    // Example win condition
    if (playerPiece.getCurrentTile() == getBoard().getTile(lastTile)) {
      log.info("Player {} has won the game!", currentPlayer.getPlayerName());

      // Increment win stats
      playerStats.incrementWins();
      log.info("Incremented win for player {}: {}.",
          currentPlayer.getPlayerId(), currentPlayer.getPlayerName());

      // Increment losses for other players
      for (Player player : turnOrder) {
        if (!player.equals(currentPlayer)
            && player.getPlayerStats() instanceof SnakesAndLaddersStats otherStats) {
          otherStats.incrementLosses();

          playerStats.incrementLosses();
          log.info("Incremented loss for player {}: {}.",
              player.getPlayerId(), player.getPlayerName());
        }
      }
      setWinner(currentPlayer);
      gameIsOver();
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
    if (playerIndex == turnOrder.size()) {
      playerIndex = 0;
    }
    Player player = turnOrder.get(playerIndex);
    playerIndex++;
    return player;
  }

  public List<Player> getAllPlayers() {
    return turnOrder;
  }
}
