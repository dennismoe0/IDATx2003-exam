package no.ntnu.idatx2003.exam2025.boardgames.service;

import java.sql.SQLException;
import java.util.LinkedHashMap;
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
    int playerId = player.getPlayerId();

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

        // Saves
        statsDao.save(playerId, stats);

        log.info(
            "Created player with ID {}: {} and assigned stats.", playerId, player.getPlayerName());
      } catch (ReflectiveOperationException | SQLException e) {
        log.error("Failed to create player or assign stats: {}", e.getMessage(), e);
      }
    }
  }

  /**
   * Loads all statistics object for the given player.
   *
   * @param player the player whose stats to load
   * @return the stats object
   * @throws SQLException if load fails
   */
  public T loadStats(Player player) throws SQLException {
    int playerId = player.getPlayerId();
    if (playerId <= 0) {
      throw new IllegalArgumentException("Player ID does not exist or has not been persisted.");
    }
    return statsDao.load(playerId);
  }

  /**
   * Loads all statistic values for the given player.
   *
   * @param player the player whose statistics to load
   * @return a list of statistic values for the player
   * @throws SQLException if loading statistics fails
   */
  public List<Integer> loadAllStats(Player player) throws SQLException {
    return loadStats(player).getStatValues();
  }

  /**
   * Loads a specific statistic value for the given player by index.
   *
   * @param player the player whose statistic to load
   * @param index  the 1-based index of the statistic to retrieve
   * @return the statistic value at the specified index
   * @throws SQLException             if loading statistics fails
   * @throws IllegalArgumentException if the index is out of range
   */
  public int loadStat(Player player, int index) throws SQLException {
    List<Integer> values = loadAllStats(player);
    if (index < 1 || index > values.size()) {
      throw new IllegalArgumentException("Stat index out of range: " + index);
    }
    return values.get(index - 1);
  }

  /**
   * Loads the statistics for the given player and returns them as a map of
   * statistic names to values.
   * For ease of display, removes manual work in the view-classes hopefully.
   *
   * @param player the player whose statistics to load
   * @return a LinkedHashMap mapping statistic names to their corresponding values
   * @throws SQLException          if loading statistics fails
   * @throws IllegalStateException if the number of statistic names and values do
   *                               not match
   */
  public LinkedHashMap<String, Integer> loadStatsAsMap(Player player) throws SQLException {
    PlayerStats stats = loadStats(player);
    List<String> names = stats.getStatNames();
    List<Integer> vals = stats.getStatValues();
    if (names.size() != vals.size()) {
      throw new IllegalStateException("Names and values size mismatch");
    }
    LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
    for (int i = 0; i < names.size(); i++) {
      map.put(names.get(i), vals.get(i));
    }
    return map;
  }

}