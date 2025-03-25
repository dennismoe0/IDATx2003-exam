package no.ntnu.idatx2003.exam2025.laddersgamextreme.dao.stats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.stats.SnakesStats;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.service.DatabaseManager;

/**
 * Data Access Object for SnakesStats.
 * Handles database operations specific to Snakes and Ladders game statistics.
 *
 * @author Dennis Moe
 */
public class SnakesStatsDao implements GameStatsDao<SnakesStats> {

  @Override
  public boolean saveStats(int playerId, SnakesStats stats) {
    // Check if stats already exist for this player using player_id only
    String checkSql = "SELECT COUNT(*) FROM snakes_and_ladders_stats WHERE player_id = ?";

    try (Connection conn = DatabaseManager.connect();
        PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
      checkStmt.setInt(1, playerId);
      ResultSet rs = checkStmt.executeQuery();

      if (rs.next() && rs.getInt(1) > 0) {
        return updateStats(playerId, stats);
      } else {
        return insertStats(playerId, stats);
      }
    } catch (SQLException e) {
      System.err.println("Error checking stats: " + e.getMessage());
      return false;
    }
  }

  /**
   * Inserts new statistics into the database.
   *
   * @param playerId The ID of the player
   * @param stats    The game statistics to insert
   * @return true if the operation was successful, false otherwise
   */
  private boolean insertStats(int playerId, SnakesStats stats) {
    String sql = "INSERT INTO snakes_and_ladders_stats " +
        "(player_id, wins, losses, games_played, " +
        "ladders_used, snakes_used, highest_dice_roll, " +
        "total_dice_rolls, sum_of_all_dice_rolls) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DatabaseManager.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, playerId);
      stmt.setInt(2, stats.getWins());
      stmt.setInt(3, stats.getLosses());
      stmt.setInt(4, stats.getGamesPlayed());
      stmt.setInt(5, stats.getLaddersUsed());
      stmt.setInt(6, stats.getSnakesUsed());
      stmt.setInt(7, stats.getHighestDiceRoll());
      stmt.setInt(8, stats.getTotalDiceRolls());
      stmt.setInt(9, stats.getSumOfAllDiceRolls());

      int rowsAffected = stmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      System.err.println("Error inserting Snakes and Ladders stats: " + e.getMessage());
      return false;
    }
  }

  /**
   * Updates existing statistics in the database.
   *
   * @param playerId The ID of the player
   * @param stats    The game statistics to update
   * @return true if the operation was successful, false otherwise
   */
  private boolean updateStats(int playerId, SnakesStats stats) {
    String sql = "UPDATE snakes_and_ladders_stats SET " +
        "wins = ?, losses = ?, games_played = ?, " +
        "ladders_used = ?, snakes_used = ?, highest_dice_roll = ?, " +
        "total_dice_rolls = ?, sum_of_all_dice_rolls = ? " +
        "WHERE player_id = ?";

    try (Connection conn = DatabaseManager.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, stats.getWins());
      stmt.setInt(2, stats.getLosses());
      stmt.setInt(3, stats.getGamesPlayed());
      stmt.setInt(4, stats.getLaddersUsed());
      stmt.setInt(5, stats.getSnakesUsed());
      stmt.setInt(6, stats.getHighestDiceRoll());
      stmt.setInt(7, stats.getTotalDiceRolls());
      stmt.setInt(8, stats.getSumOfAllDiceRolls());
      stmt.setInt(9, playerId);

      int rowsAffected = stmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      System.err.println("Error updating Snakes and Ladders stats: " + e.getMessage());
      return false;
    }
  }

  @Override
  public SnakesStats loadStats(int playerId) {
    String sql = "SELECT * FROM snakes_and_ladders_stats WHERE player_id = ?";

    try (Connection conn = DatabaseManager.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, playerId);

      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return extractStatsFromResultSet(rs);
      } else {
        // No stats found, return a new instance
        return new SnakesStats();
      }
    } catch (SQLException e) {
      System.err.println("Error loading Snakes and Ladders stats: " + e.getMessage());
      return new SnakesStats();
    }
  }

  @Override
  public boolean deleteStats(int playerId) {
    String sql = "DELETE FROM snakes_and_ladders_stats WHERE player_id = ?";

    try (Connection conn = DatabaseManager.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, playerId);

      int rowsAffected = stmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      System.err.println("Error deleting Snakes and Ladders stats: " + e.getMessage());
      return false;
    }
  }

  private SnakesStats extractStatsFromResultSet(ResultSet rs) throws SQLException {
    SnakesStats stats = new SnakesStats();

    // We need to reset the default values since we'll be overwriting them
    stats.resetStats();

    // Manually set wins and losses to match database values
    // We can't use addWin() or addLoss() as they would increment gamesPlayed
    for (int i = 0; i < rs.getInt("wins"); i++) {
      stats.addWin();
    }

    for (int i = 0; i < rs.getInt("losses"); i++) {
      stats.addLoss();
    }

    // Set game-specific statistics
    for (int i = 0; i < rs.getInt("ladders_used"); i++) {
      stats.incrementLaddersUsed();
    }

    for (int i = 0; i < rs.getInt("snakes_used"); i++) {
      stats.incrementSnakesUsed();
    }

    // Handle dice rolls - we recreate the statistics rather than the exact rolls
    int totalDiceRolls = rs.getInt("total_dice_rolls");
    int sumOfAllDiceRolls = rs.getInt("sum_of_all_dice_rolls");
    int highestDiceRoll = rs.getInt("highest_dice_roll");

    // If we have dice rolls, we need to simulate them to rebuild the state
    if (totalDiceRolls > 0) {
      // First, record the highest roll
      stats.recordDiceRoll(highestDiceRoll);

      // Then, add enough average rolls to reach the correct total and sum
      int remainingRolls = totalDiceRolls - 1; // -1 because we already recorded the highest
      int remainingSum = sumOfAllDiceRolls - highestDiceRoll;

      if (remainingRolls > 0) {
        int avgRoll = remainingSum / remainingRolls;
        int remainder = remainingSum % remainingRolls;

        // Record average rolls
        for (int i = 0; i < remainingRolls - remainder; i++) {
          stats.recordDiceRoll(avgRoll);
        }

        // Distribute the remainder
        for (int i = 0; i < remainder; i++) {
          stats.recordDiceRoll(avgRoll + 1);
        }
      }
    }

    return stats;
  }
}