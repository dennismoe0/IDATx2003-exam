package no.ntnu.idatx2003.exam2025.laddersgamextreme.service;

import java.sql.Connection;
import java.sql.SQLException;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.dao.PlayerDAO;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.Player;

/**
 * Manages the stats, name and other information.
 * Also interacts with the DAO files for Player and PlayerStats.
 *
 * @author Dennis Moe
 */
public class PlayerManager {
  private final PlayerDAO playerDAO;

  /**
   * Constructs a PlayerManager with the specified database connection.
   *
   * @param connection the database connection to be used by the PlayerDAO
   */
  public PlayerManager(Connection connection) {
    this.playerDAO = new PlayerDAO(connection);
  }

  /**
   * Creates a new player with the given name and age.
   *
   * @param playerName the name of the player to be created
   * @param playerAge  the age of the player to be created
   * @return the newly created Player object, or null if there was an error
   */
  public Player createPlayer(String playerName, int playerAge) {
    try {
      int newPlayerId = playerDAO.getNextPlayerId(); // Get next Id
      Player newPlayer = new Player(newPlayerId, playerName, playerAge);
      playerDAO.addPlayer(newPlayer); // Store in DB
      return newPlayer;
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Retrieves an existing player by name or creates a new player if one does not
   * exist.
   *
   * @param playerName the name of the player to retrieve or create
   * @param playerAge  the age of the player to create if a new player is needed
   * @return the existing or newly created Player object, or null if an error
   *         occurs
   */
  public Player getOrCreatePlayer(String playerName, int playerAge) {
    try {
      // Check if the player already exists
      Player existingPlayer = playerDAO.getPlayerByName(playerName);
      if (existingPlayer != null) {
        return existingPlayer;
      }

      // If not, create a new player
      int newPlayerId = playerDAO.getNextPlayerId();
      Player newPlayer = new Player(newPlayerId, playerName, playerAge);
      playerDAO.addPlayer(newPlayer);
      return newPlayer;
    } catch (SQLException e) {
      return null;
    }
  }
}
