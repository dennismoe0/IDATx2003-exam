package no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.SnakesAndLaddersStats;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

/**
 * Implementation of the SnakesAndLaddersStatsDao interface for managing
 * Snakes and Ladders statistics in the database.
 */
public class SnakesAndLaddersStatsDaoImpl implements SnakesAndLaddersStatsDao {
  private static final Logger log = Log.get(SnakesAndLaddersStatsDaoImpl.class);
  private final Connection connection;

  // Could use text block
  private static final String SQL_INSERT = "INSERT INTO snakes_and_ladders_stats "
      + "(player_id, wins, losses, games_played, ladders_used, snakes_used, "
      + "highest_dice_roll, total_dice_rolls, total_moves) "
      + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) "
      + "ON CONFLICT(player_id) DO UPDATE SET "
      + "wins               = excluded.wins, "
      + "losses             = excluded.losses, "
      + "games_played       = excluded.games_played, "
      + "ladders_used       = excluded.ladders_used, "
      + "snakes_used        = excluded.snakes_used, "
      + "highest_dice_roll  = excluded.highest_dice_roll, "
      + "total_dice_rolls   = excluded.total_dice_rolls, "
      + "total_moves        = excluded.total_moves;";

  private static final String SQL_SELECT = "SELECT wins, losses, games_played, ladders_used,"
      + "snakes_used, highest_dice_roll, total_dice_rolls, total_moves "
      + "FROM snakes_and_ladders_stats WHERE player_id = ?";

  /**
   * Constructs a new SnakesAndLaddersStatsDaoImpl with the given database
   * connection.
   *
   * @param connection the SQL database connection to use
   */
  public SnakesAndLaddersStatsDaoImpl(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void save(int playerId, SnakesAndLaddersStats stats) throws SQLException {

    String checkPlayerSql = "SELECT 1 FROM players WHERE player_id = ?";
    try (PreparedStatement checkStmt = connection.prepareStatement(checkPlayerSql)) {
      checkStmt.setInt(1, playerId);
      ResultSet rs = checkStmt.executeQuery();
      if (!rs.next()) {
        throw new SQLException("Cannot save stats: Player with ID "
            + playerId
            + " does not exist.");
      }
    }

    log.info("Saving Snakes and Ladders stats for player ID {}: {}", playerId, stats);

    try (PreparedStatement stmt = connection.prepareStatement(SQL_INSERT)) {
      stmt.setInt(1, playerId);
      stmt.setInt(2, stats.getWins());
      stmt.setInt(3, stats.getLosses());
      stmt.setInt(4, stats.getGamesPlayed());
      stmt.setInt(5, stats.getLaddersUsed());
      stmt.setInt(6, stats.getSnakesUsed());
      stmt.setInt(7, stats.getHighestDiceRoll());
      stmt.setInt(8, stats.getTotalDiceRolls());
      stmt.setInt(9, stats.getTotalMoveCount());
      stmt.executeUpdate();
      log.debug("Stats saved for player {}", playerId);
    }
  }

  @Override
  public SnakesAndLaddersStats load(int playerId) throws SQLException {
    String sql = "SELECT * FROM snakes_and_ladders_stats WHERE player_id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, playerId);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        SnakesAndLaddersStats stats = new SnakesAndLaddersStats();
        stats.setWins(rs.getInt("wins"));
        stats.setLosses(rs.getInt("losses"));
        stats.setGamesPlayed(rs.getInt("games_played"));
        stats.setLaddersUsed(rs.getInt("ladders_used"));
        stats.setSnakesUsed(rs.getInt("snakes_used"));
        stats.setHighestDiceRoll(rs.getInt("highest_dice_roll"));
        stats.setTotalDiceRolls(rs.getInt("total_dice_rolls"));
        stats.setTotalMoveCount(rs.getInt("total_moves"));
        log.debug("Loaded stats for player {}", playerId);
        return stats;
      }
    }
    log.warn("No SnakesAndLaddersStats found for player {}", playerId);
    return new SnakesAndLaddersStats(); // Return null if no stats are found
  }
}