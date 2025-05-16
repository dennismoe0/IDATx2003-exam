package no.ntnu.idatx2003.exam2025.boardgames.controller;

import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;

import java.util.List;
import java.sql.SQLException;

/**
 * Controller class for managing the player list view.
 * Handles retrieval and deletion of players using the PlayerDaoImpl.
 */
public class PlayerListViewController {

  private final PlayerDaoImpl playerDao;
  // needs reference to the DAO for loading saved players.
  // needs methods for populating the list view I guess?
  // private final PlayerDaoImpl playerDAO;
  // private final List<Player> playerList;

  /**
   * Constructs a PlayerListViewController with the specified PlayerDaoImpl.
   *
   * @param playerDao the PlayerDaoImpl used to access player data
   */
  public PlayerListViewController(PlayerDaoImpl playerDao) {
    this.playerDao = playerDao;
  }

  /**
   * Retrieves all players from database.
   *
   * @return a list of all players
   * @throws SQLException if a database access error occurs
   */
  public List<Player> getAllPlayers() throws SQLException {
    return playerDao.getAllPlayers();
  }

  /**
   * Deletes a player from the database by their player ID.
   *
   * @param playerId the ID of the player to delete
   * @throws SQLException if a database access error occurs
   */
  public void deletePlayer(int playerId) throws SQLException {
    playerDao.delete(playerId);
  }
}
