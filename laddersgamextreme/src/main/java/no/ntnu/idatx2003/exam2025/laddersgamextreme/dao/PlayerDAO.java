package no.ntnu.idatx2003.exam2025.laddersgamextreme.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.Player;

/**
 * Data Access Object (DAO) class for managing player-related database
 * operations.
 */
public class PlayerDAO {
  private final Connection connection;

  /**
   * Constructs a new PlayerDAO with the specified database connection.
   *
   * @param connection the database connection to be used by this DAO
   */
  public PlayerDAO(Connection connection) {
    this.connection = connection;
  }

  /**
   * Gets the next available id instead of using an instance-based increment in
   * the Player class.
   */
  public int getNextPlayerId() throws SQLException {
    String sql = "SELECT MAX(player_id) FROM players"; // statement to execute

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      ResultSet rs = stmt.executeQuery(); // Executes query in db
      if (rs.next()) {
        int maxId = rs.getInt(1); // position
        return maxId + 1; // Increment by 1 for new users
      }
    }
    return 1; // If no player exist, this is the first id.
  }

  /**
   * Adds a new player to the database.
   *
   * @param player the Player object containing the player's details
   * @throws SQLException if a database access error occurs
   */
  public void addPlayer(Player player) throws SQLException {
    String sql = "INSERT INTO players (player_name, player_age) VALUES (?, ?)";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, player.getPlayerName());
      stmt.setInt(2, player.getPlayerAge());
      stmt.executeUpdate();
    }
  }

  /**
   * Removes a player from the database.
   *
   * @param playerId the ID of the player to be removed
   * @throws SQLException if a database access error occurs
   */
  public void removePlayer(int playerId) throws SQLException {
    String sql = "DELETE FROM players WHERE player_id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, playerId);
      stmt.executeUpdate();
    }
  }

  public Player getPlayerByName(String playerName) throws SQLException {
    String sql = "SELECT * FROM players WHERE player_name = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, playerName);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return new Player(rs.getInt("player_id"), rs.getString("player_name"), rs.getInt("age"));
      }
    }
    return null;
  }
}