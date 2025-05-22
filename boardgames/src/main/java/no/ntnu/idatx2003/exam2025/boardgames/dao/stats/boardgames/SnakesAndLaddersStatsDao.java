package no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames;

import java.sql.SQLException;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.SnakesAndLaddersStats;

/**
 * Data Access Object (DAO) interface for managing statistics related to the
 * Snakes and Ladders game.
 * Provides methods to save and load player statistics.
 */
public interface SnakesAndLaddersStatsDao extends GameStatsDao<SnakesAndLaddersStats> {

  /**
   * Saves the statistics for a specific player in the Snakes and Ladders game.
   *
   * @param playerId the ID of the player
   * @param stats    the statistics to save
   * @throws SQLException if a database access error occurs
   */
  @Override
  void save(int playerId, SnakesAndLaddersStats stats) throws SQLException;

  /**
   * Loads the statistics for a specific player in the Snakes and Ladders game.
   *
   * @param playerId the ID of the player
   * @return the loaded statistics
   * @throws SQLException if a database access error occurs
   */
  @Override
  SnakesAndLaddersStats load(int playerId) throws SQLException;
}