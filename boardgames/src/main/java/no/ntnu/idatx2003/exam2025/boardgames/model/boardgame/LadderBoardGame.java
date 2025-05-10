package no.ntnu.idatx2003.exam2025.boardgames.model.boardgame;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;
import no.ntnu.idatx2003.exam2025.boardgames.model.Die;
import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.SnakesAndLaddersStats;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;
import no.ntnu.idatx2003.exam2025.boardgames.service.DatabaseManager;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.SnakesAndLaddersStatsDao;
import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.SnakesAndLaddersStatsDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.util.LadderGameMessage;

/**
 * A Board Game class representing Snakes n Ladders / Stigespillet.
 */
public final class LadderBoardGame extends BoardGame {
  private Dice dice;
  private List<Player> players;
  private Player currentPlayer;
  private int playerIndex;
  private boolean gameIsOver;
  private LadderGameMoveHistory moveHistory = new LadderGameMoveHistory();
  private final SnakesAndLaddersStatsDao statsDao;
  private static final Logger log = Log.get(LadderBoardGame.class);

  /**
   * The default constructor for the Ladder Game class.
   *
   * @param board   a collection of tiles to act as a game board.
   * @param players the players participating in the game, to keep track of turns.
   */
  public LadderBoardGame(Board board, List<Player> players, Connection connection) {
    this.players = new ArrayList<>(players);
    super.setBoard(board);
    this.statsDao = new SnakesAndLaddersStatsDaoImpl(connection) {
    };
    setUp(this.players);
  }


  public Dice getDice() {
    return dice;
  }

  public LadderGameMoveHistory getMoveHistory() {
    return moveHistory;
  }

  @Override
  public void setUp(List<Player> players) {
    gameIsOver = false;
    dice = new Dice();
    dice.addDice(new Die(6));

    List<GamePiece> pieces = new ArrayList<>();
    for (Player player : players) {
      pieces.clear();
      Tile startingTile = getBoard().getTile(1);
      if (startingTile == null) {
        throw new IllegalArgumentException("Strting tile (tile 1) does not exist.");
      }
      pieces.add(new GamePiece(startingTile)); // Null as placeholder
      super.addPlayerPieces(player, pieces);
    }
    playerIndex = 0;
    currentPlayer = players.get(playerIndex);
  }

  @Override
  public void takeTurn() {
    // Get the current player for this turn
    currentPlayer = getNextPlayer();
    log.info("Starting turn for player index: {}, player: {}", playerIndex, currentPlayer.getPlayerName());

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

    int endTile = playerPiece.getCurrentTile().getId();
    moveHistory.addMessage(new LadderGameMessage(currentPlayer, startTile, endTile, diceRoll));

    // Log the player's move
    log.info(
        "Player {} moved. Current tile: {}",
        currentPlayer.getPlayerName(), playerPiece.getCurrentTile());

    // Update stats for the current player
    if (!(currentPlayer.getPlayerStats() instanceof SnakesAndLaddersStats stats)) {
      throw new IllegalStateException("PlayerStats is not an instance of SnakesAndLaddersStats");
    }

    // Update dice roll and movement stats
    stats.incrementDiceRoll();
    stats.incrementMove(diceRoll);

    try {
      statsDao.save(currentPlayer.getPlayerId(), stats);
    } catch (SQLException e) {
      log.error(
          "Failed to save stats for player ID {}: {}",
          currentPlayer.getPlayerId(), e.getMessage());
    }

    // Check if the player has won
    int winTile = 90; // Example win condition
    if (playerPiece.getCurrentTile() == getBoard().getTile(winTile)) {
      gameIsOver = true;
      log.info("Player {} has won the game!", currentPlayer.getPlayerName());

      // Update win stats
      stats.incrementWins();
      try {
        statsDao.save(currentPlayer.getPlayerId(), stats);
      } catch (SQLException e) {
        log.error(
            "Failed to save stats for player ID {}: {}",
            currentPlayer.getPlayerId(), e.getMessage());
      }

      // Update losses for other players
      for (Player player : players) {
        if (!player.equals(currentPlayer)
            && player.getPlayerStats() instanceof SnakesAndLaddersStats otherStats) {
          otherStats.incrementLosses();
          try {
            statsDao.save(player.getPlayerId(), otherStats);
          } catch (SQLException e) {
            log.error(
                "Failed to save stats for player ID {}: {}",
                player.getPlayerId(), e.getMessage());
          }
        }
      }
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
    if (playerIndex == players.size()) {
      playerIndex = 0;
    }
    Player player = players.get(playerIndex);
    playerIndex++;
    return player;
  }

  public boolean isGameOver() {
    return gameIsOver;
  }

  // use observer pattern to track player piece positions and fire a "game over"
  // event.

}
