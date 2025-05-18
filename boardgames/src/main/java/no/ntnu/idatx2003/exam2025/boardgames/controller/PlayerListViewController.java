package no.ntnu.idatx2003.exam2025.boardgames.controller;

import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.model.GameSession;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;

import java.util.List;
import java.util.Map;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import no.ntnu.idatx2003.exam2025.boardgames.service.StatsManager;

/**
 * Controller class for managing the player list view.
 * Handles retrieval and deletion of players using the PlayerDaoImpl.
 */
public class PlayerListViewController {

  private final PlayerDaoImpl playerDao;
  private final Map<String, StatsManager<?>> statsManagers;
  private final GameSession gameSession;
  // needs reference to the DAO for loading saved players.
  // needs methods for populating the list view I guess?
  // private final PlayerDaoImpl playerDAO;
  // private final List<Player> playerList;

  /**
   * Constructs a PlayerListViewController with the specified PlayerDaoImpl.
   *
   * @param playerDao the PlayerDaoImpl used to access player data
   */
  public PlayerListViewController(
      PlayerDaoImpl playerDao, Map<String, StatsManager<?>> statsManagers, GameSession gameSession) {
    this.playerDao = playerDao;
    this.statsManagers = statsManagers;
    this.gameSession = gameSession;
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

  public void addPlayerToGameSession(Player player) {
    gameSession.addPlayer(player);
  }

  public void removePlayerFromGameSession(Player player) {
    gameSession.removePlayer(player);
  }

  /**
   * Retrieves the statistics map for a given player and game.
   *
   * @param player   the player whose statistics are to be retrieved
   * @param gameName the name of the game for which statistics are needed
   * @return a LinkedHashMap containing statistic names and their values
   * @throws SQLException             if a database access error occurs
   * @throws IllegalArgumentException if no StatsManager is found for the
   *                                  specified game
   */
  public LinkedHashMap<String, Integer> getPlayerStatsMap(
      Player player, String gameName) throws SQLException {
    StatsManager<?> manager = statsManagers.get(gameName);
    if (manager == null) {
      throw new IllegalArgumentException("No statsmanager for game: " + gameName);
    }
    LinkedHashMap<String, Integer> stats = ((StatsManager<?>) manager).loadStatsAsMap(player);
    return stats;
  }

  public List<String> getSupportedGames() {
    return new ArrayList<>(statsManagers.keySet());
  }
}
