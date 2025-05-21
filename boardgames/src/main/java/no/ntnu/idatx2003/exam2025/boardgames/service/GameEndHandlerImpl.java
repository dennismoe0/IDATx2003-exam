package no.ntnu.idatx2003.exam2025.boardgames.service;

import java.sql.SQLException;
import org.slf4j.Logger;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.BoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.PlayerStats;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;

public class GameEndHandlerImpl implements GameEventHandler {
  private static final Logger log = Log.get(GameEventHandlerImpl.class);
  private final StatsManager<PlayerStats> statsManager;
  private final AudioManager audioManager; // for playing victory sound

  public GameEndHandlerImpl(StatsManager<PlayerStats> statsManager, AudioManager audioManager) {
    this.statsManager = statsManager;
    this.audioManager = audioManager;
  }

  @Override
  public void handleGameOver(BoardGame game) {
    // Persist stats for each player using StatsManager
    for (Player player : game.getPlayers()) {

      try {
        statsManager.save(player, player.getPlayerStats());
      } catch (SQLException ex) {
        log.error("Failed to persist player stats for player {}, stats: {}. ", player.getPlayerId(),
            player.getPlayerStats(), ex);
      }
    }
    log.info("Game over stats persisted.");

    // Play victory sound effect
    // audioManager.playVictorySound();
  }

}
