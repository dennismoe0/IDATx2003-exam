package no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.SnakesAndLaddersStats;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

public class SnakesAndLaddersStatsDaoImpl implements SnakesAndLaddersStatsDao {
  private static final Logger log = Log.get(SnakesAndLaddersStatsDaoImpl.class);
  private final Connection connection;

  public SnakesAndLaddersStatsDaoImpl(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void save(int playerId, SnakesAndLaddersStats stats) throws SQLException {
    String sql = """
          INSERT INTO snakes_and_ladders_stats (
            player_id, wins, losses, games_played,
            ladders_used, snakes_used, highest_dice_roll,
            total_dice_rolls, sum_of_all_dice_rolls, total_moves
          ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
          ON CONFLICT(player_id) DO UPDATE SET
            wins = excluded.wins,
            losses = excluded.losses,
            games_played = excluded.games_played,
            ladders_used = excluded.ladders_used,
            snakes_used = excluded.snakes_used,
            highest_dice_roll = excluded.highest_dice_roll,
            total_dice_rolls = excluded.total_dice_rolls,
            sum_of_all_dice_rolls = excluded.sum_of_all_dice_rolls,
            total_moves = excluded.total_moves;
        """;

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, playerId);
      stmt.setInt(2, stats.getWins());
      stmt.setInt(3, stats.getLosses());
      stmt.setInt(4, stats.getGamesPlayed());
      stmt.setInt(5, stats.getLaddersUsed());
      stmt.setInt(6, stats.getSnakesUsed());
      stmt.setInt(7, stats.getHighestDiceRoll());
      stmt.setInt(8, stats.getTotalDiceRolls());
      stmt.setInt(9, stats.getSumOfAllDiceRolls());
      stmt.setInt(10, stats.getTotalMoveCount());
      stmt.executeUpdate();
      log.debug("Saved SnakesAndLaddersStats for player {}", playerId);
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
        stats.setSumOfAllDiceRolls(rs.getInt("sum_of_all_dice_rolls"));
        stats.setTotalMoveCount(rs.getInt("total_moves"));
        log.debug("Loaded SnakesAndLaddersStats for player {}", playerId);
        return stats;
      }
    }
    log.warn("No SnakesAndLaddersStats found for player {}", playerId);
    return new SnakesAndLaddersStats();
  }
}