package no.ntnu.idatx2003.exam2025.laddersgamextreme.dao.stats;

import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.stats.GameStats;

/**
 * Data Access Object interface for GameStats classes.
 * Defines methods for loading, saving, and deleting game statistics.
 *
 * @param <T> The specific GameStats subclass this DAO handles
 * @author Dennis Moe
 */
public interface GameStatsDao<T extends GameStats> {

  /**
   * Saves or updates the statistics in the database.
   *
   * @param playerId The ID of the player
   * @param stats    The game statistics to save
   * @return true if the operation was successful, false otherwise
   */
  boolean saveStats(int playerId, T stats);

  /**
   * Loads statistics for a player from the database.
   *
   * @param playerId The ID of the player
   * @return The game statistics for the player, or a new instance if none exist
   */
  T loadStats(int playerId);

  /**
   * Deletes statistics for a player from the database.
   *
   * @param playerId The ID of the player
   * @return true if the operation was successful, false otherwise
   */
  boolean deleteStats(int playerId);
}