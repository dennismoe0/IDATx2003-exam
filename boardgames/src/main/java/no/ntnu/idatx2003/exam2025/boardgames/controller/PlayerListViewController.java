package no.ntnu.idatx2003.exam2025.boardgames.controller;

import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;

import java.util.List;
import java.sql.SQLException;

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
}
