package no.ntnu.idatx2003.exam2025.boardgames.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDao;
import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.GameStatsDao;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.PlayerStats;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;

/**
 * Manages statistics for players in board games.
 *
 * @param <T> The type of player statistics being managed, extending
 *            PlayerStats.
 */
public class StatsManager<T extends PlayerStats> {
  private final PlayerDao playerDao;
  private final GameStatsDao<T> statsDao; // T = type of stats
  private static final Logger log = Log.get(StatsManager.class);

  /**
   * Constructs a StatsManager with the specified PlayerDao and GameStatsDao.
   *
   * @param playerDao The DAO for managing players.
   * @param statsDao  The DAO for managing game statistics.
   */
  public StatsManager(PlayerDao playerDao, GameStatsDao<T> statsDao) {
    this.playerDao = playerDao;
    this.statsDao = statsDao;
  }

  /**
   * Saves the statistics for a given player.
   *
   * @param player The player whose statistics are being saved.
   * @param stats  The statistics to save for the player.
   * @throws SQLException If an error occurs while saving the statistics.
   */
  public void save(Player player, T stats) throws SQLException {
    Integer playerId = player.getPlayerId();

    // Could make it automatically create a player, but this should be handled
    // through UI
    if (playerId <= 0) {
      throw new IllegalArgumentException("Player ID does not exist or has not been persisted.");
    }
    statsDao.save(playerId, stats);
    log.info("Saved stats for Player {}, for game stats {}", playerId, stats);
  }

  /**
   * Creates and persists players with their associated statistics.
   *
   * @param players    The list of players to be created and persisted.
   * @param statsClass The class type of the statistics to be assigned to the
   *                   players.
   */
  public void createPersistedPlayerWithStats(List<Player> players, Class<? extends T> statsClass) {
    for (Player player : players) {
      try {
        // Database creaiton, not actual player object creation
        int playerId = playerDao.create(player);
        player.setPlayerId(playerId);

        // Create a new stats instance and assign to player
        T stats = statsClass.getDeclaredConstructor().newInstance();
        player.setPlayerStats(stats);

        log.info(
            "Created player with ID {}: {} and assigned stats.", playerId, player.getPlayerName());
      } catch (ReflectiveOperationException | SQLException e) {
        log.error("Failed to create player or assign stats: {}", e.getMessage(), e);
      }
    }
  }
}