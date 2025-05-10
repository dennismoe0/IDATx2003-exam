package no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames;

import java.sql.SQLException;

/**
 * A Data Access Object (DAO) interface for managing game statistics.
 *
 * @param <T> the type of the statistics object
 */
public interface GameStatsDao<T> {

  /**
   * Saves the game statistics for a specific player.
   *
   * @param playerId the ID of the player
   * @param stats    the statistics object to save
   * @throws SQLException if a database access error occurs
   */
  void save(int playerId, T stats) throws SQLException;

  /**
   * Loads the game statistics for a specific player.
   *
   * @param playerId the ID of the player
   * @return the statistics object for the player
   * @throws SQLException if a database access error occurs
   */
  T load(int playerId) throws SQLException;
}
