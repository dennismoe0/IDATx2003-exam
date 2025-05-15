package no.ntnu.idatx2003.exam2025.boardgames.dao.player;

import java.sql.SQLException;
import java.util.List;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;

/**
 * Data Access Object (DAO) interface for managing Player entities.
 * Provides methods for creating, retrieving, and deleting players in the
 * database.
 */
public interface PlayerDao {

  /**
   * Creates a new player in the database.
   *
   * @param player The Player object to be created.
   * @return The generated player ID for the newly created player.
   * @throws SQLException If a database access error occurs.
   */
  int create(Player player) throws SQLException;

  /**
   * Finds a player in the database by their ID.
   *
   * @param playerId The ID of the player to retrieve.
   * @return The Player object corresponding to the given ID.
   * @throws SQLException If a database access error occurs or the player is not
   *                      found.
   */
  Player findById(int playerId) throws SQLException;

  /**
   * Deletes a player from the database by their ID.
   *
   * @param playerId The ID of the player to delete.
   * @throws SQLException If a database access error occurs or the player cannot
   *                      be deleted.
   */
  void delete(int playerId) throws SQLException;

  /**
   * Retrieves a list of all player names from the database.
   *
   * @return A list of player names.
   * @throws SQLException If a database access error occurs.
   */
  List<String> getAllPlayerNames() throws SQLException;

  /**
   * Retrieves a list of all player IDs from the database.
   *
   * @return A list of player IDs.
   * @throws SQLException If a database access error occurs.
   */
  List<Integer> getAllPlayerIds() throws SQLException;
}
