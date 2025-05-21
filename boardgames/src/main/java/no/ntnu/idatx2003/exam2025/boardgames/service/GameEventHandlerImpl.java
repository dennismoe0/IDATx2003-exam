package no.ntnu.idatx2003.exam2025.boardgames.service;

import java.sql.SQLException;
import org.slf4j.Logger;

import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.SnakesAndLaddersStatsDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDao;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.BoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderBoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.LudoStats;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.SnakesAndLaddersStats;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;

/**
 * Implementation of the GameEventHandler interface that handles game events
 * such as game over
 * and persists statistics for different board games.
 */
public class GameEventHandlerImpl implements GameEventHandler {

  private static final Logger log = Log.get(GameEventHandlerImpl.class);
  private final StatsManager<SnakesAndLaddersStats> snakesStatsManager;
  // private final StatsManager<LudoStats> ludoStatsManager;
  private final AudioManager audioManager;

  /**
   * Constructs a new GameEventHandlerImpl, initializing the stats manager and
   * audio manager.
   *
   * @throws SQLException if a database access error occurs
   */
  public GameEventHandlerImpl() throws SQLException {
    PlayerDao playerDao = new PlayerDaoImpl(DatabaseManager.connect());
    snakesStatsManager = new StatsManager<>(
        playerDao, new SnakesAndLaddersStatsDaoImpl(DatabaseManager.connect()));
    audioManager = new AudioManager();
  }

  @Override
  public void handleGameOver(BoardGame game) {
    // Persist stats for each player using StatsManager
    if (game instanceof LadderBoardGame ladder) {
      persistSnakesAndLadders(ladder);
    } else {
      log.warn("Unhandled game type.");
    }
    audioManager.playVictorySound();
  }

  /**
   * Persists Snakes and Ladders statistics for all players in the given
   * LadderBoardGame.
   *
   * @param ladderBoard the LadderBoardGame whose player stats should be saved
   */
  public void persistSnakesAndLadders(LadderBoardGame ladderBoard) {
    if (ladderBoard instanceof LadderBoardGame ladder) {
      for (Player p : ladder.getPlayers()) {
        try {
          snakesStatsManager.save(p, (SnakesAndLaddersStats) p.getPlayerStats());
        } catch (SQLException e) {
          log.error("Failed to save S&L stats for {}: {}", p.getPlayerName(), e.getMessage(), e);
        }
      }
      log.info("Snakes & Ladders stats saved.");
    } else {
      log.error("Not an instance of Snakes And Ladders.");
    }
  }

  // public void persistLudo() {}
}
