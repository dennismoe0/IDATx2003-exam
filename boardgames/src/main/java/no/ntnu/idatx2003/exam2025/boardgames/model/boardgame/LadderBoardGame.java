package no.ntnu.idatx2003.exam2025.boardgames.model.boardgame;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import no.ntnu.idatx2003.exam2025.boardgames.service.DatabaseManager;

/**
 * A Board Game class representing Snakes n Ladders / Stigespillet.
 */
public final class LadderBoardGame extends BoardGame {
  private Dice dice;
  private GamePiece currentGamePiece;
  private List<Player> players;
  private Player currentPlayer;
  private int playerIndex;
  private boolean gameIsOver;
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
    setUp(players);
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
    currentPlayer = getNextPlayer();
    int diceRoll = dice.rollAllDiceSum();
    super.getFirstPlayerPiece(currentPlayer).move(diceRoll);

    // Stat registration part
    SnakesAndLaddersStats stats;

    if (currentPlayer.getPlayerStats() instanceof SnakesAndLaddersStats) {
      stats = (SnakesAndLaddersStats) currentPlayer.getPlayerStats();
    } else {
      throw new IllegalStateException("PlayerStats is not and instance of SnakesAndLaddersStats");
    }

    stats.incrementDiceRoll();
    stats.incrementMove(diceRoll);

    try {
      statsDao.save(currentPlayer.getPlayerId(), stats); // Save updated stats
    } catch (SQLException e) {
      log.error(
          "Failed to save stats for player ID {}: {}",
          currentPlayer.getPlayerId(), e.getMessage());
    }

    int winTile = 90;
    if (super.getFirstPlayerPiece(currentPlayer).getCurrentTile() == getBoard().getTile(winTile)) {
      gameIsOver = true;

      // Stats for game ending
      stats.incrementWins();
      try {
        statsDao.save(currentPlayer.getPlayerId(), stats);
      } catch (SQLException e) {
        log.error(
            "Failed to save stats for player ID {}: {}",
            currentPlayer.getPlayerId(), e.getMessage());
      }

      for (Player player : players) {
        if (!player.equals(currentPlayer)) {
          // Using "otherStats" to refer to the non-turn-taking player
          SnakesAndLaddersStats otherStats = (SnakesAndLaddersStats) player.getPlayerStats();
          otherStats.incrementLosses();
          try {
            statsDao.save(player.getPlayerId(), otherStats);
          } catch (SQLException e) {
            log.error(
                "Failed to save stats for player ID {}: {}",
                currentPlayer.getPlayerId(), e.getMessage());
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
    playerIndex = (playerIndex + 1) % players.size();
    return players.get(playerIndex);
  }

  public boolean isGameOver() {
    return gameIsOver;
  }

  // use observer pattern to track player piece positions and fire a "game over"
  // event.

}
