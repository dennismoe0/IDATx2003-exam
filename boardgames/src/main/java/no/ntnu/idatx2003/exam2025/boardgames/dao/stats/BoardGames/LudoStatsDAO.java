package no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames;

import java.sql.SQLException;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.LudoStats;

/**
 * Data Access Object (DAO) interface for managing statistics related to the
 * game Ludo.
 * Provides methods to save and load Ludo statistics for a specific player.
 */
public interface LudoStatsDao {

  /**
   * Saves the Ludo statistics for a specific player.
   *
   * @param playerId The unique identifier of the player.
   * @param stats    The LudoStats object containing the player's statistics to be
   *                 saved.
   * @throws SQLException If a database access error occurs or the save operation
   *                      fails.
   */
  void save(int playerId, LudoStats stats) throws SQLException;

  /**
   * Loads the Ludo statistics for a specific player.
   *
   * @param playerId The unique identifier of the player.
   * @return The LudoStats object containing the player's statistics.
   * @throws SQLException If a database access error occurs or the load operation
   *                      fails.
   */
  LudoStats load(int playerId) throws SQLException;
}
