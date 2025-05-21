package no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames;

import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.LudoStats;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LudoStatsDaoImpl implements LudoStatsDao {
  private static final Logger log = Log.get(LudoStatsDaoImpl.class);
  private final Connection connection;

  public LudoStatsDaoImpl(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void save(int playerId, LudoStats stats) throws SQLException {

    String sql = """
                  INSERT INTO ludo_stats (
                    player_id, wins, losses, games_played,
                    pieces_completed, double_six_rolls, pieces_knocked,
                    total_moves, total_dice_rolls
                  ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                  ON CONFLICT(player_id) DO UPDATE SET
                    wins = wins + excluded.wins,
                    losses = losses + excluded.losses,
                    games_played = games_played + excluded.games_played,
                    pieces_completed = pieces_completed + excluded.pieces_completed,
                    double_six_rolls = double_six_rolls + excluded.double_six_rolls,
                    pieces_knocked = pieces_knocked + excluded.pieces_knocked,
                    total_moves = total_moves + excluded.total_moves,
                    total_dice_rolls = total_dice_rolls + excluded.total_dice_rolls;
        """;

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, playerId);
      stmt.setInt(2, stats.getWins());
      stmt.setInt(3, stats.getLosses());
      stmt.setInt(4, stats.getGamesPlayed());
      stmt.setInt(5, stats.getPiecesCompleted());
      stmt.setInt(6, stats.getDoubleSixRolls());
      stmt.setInt(7, stats.getPiecesKnocked());
      stmt.setInt(8, stats.getTotalMoveCount());
      stmt.setInt(9, stats.getTotalDiceRolls());
      stmt.executeUpdate();
      log.debug("Saved LudoStats for player {}", playerId);
    }
  }

  @Override
  public LudoStats load(int playerId) throws SQLException {
    String sql = "SELECT * FROM ludo_stats WHERE player_id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, playerId);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        LudoStats stats = new LudoStats();
        stats.setWins(rs.getInt("wins"));
        stats.setLosses(rs.getInt("losses"));
        stats.setGamesPlayed(rs.getInt("games_played"));
        stats.setPiecesCompleted(rs.getInt("pieces_completed"));
        stats.setDoubleSixRolls(rs.getInt("double_six_rolls"));
        stats.setPiecesKnocked(rs.getInt("pieces_knocked"));
        stats.setTotalMoveCount(rs.getInt("total_moves"));
        stats.setTotalDiceRolls(rs.getInt("total_dice_rolls"));
        log.debug("Loaded LudoStats for player {}", playerId);
        return stats;
      }
    }
    log.warn("No LudoStats found for player {}", playerId);
    return new LudoStats();
  }
}
