package no.ntnu.idatx2003.exam2025.boardgames.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;

/**
 * Class manages players as part of the program as a whole, outside a game.
 */
public class PlayerManager {
  private final Set<Player> players;

  /**
   * The default constructor, instantiates a new Set for keeping track of players.
   */
  public PlayerManager() {
    players = new HashSet<Player>();
  }

  /**
   * Method for adding a player to the current set.
   *
   * @param player the player object to be added.
   */
  public void addPlayer(Player player) {
    players.add(player);
  }

  /**
   * Method for removing a player from the set.
   *
   * @param player the player to be removed.
   */
  public void removePlayer(Player player) {
    players.remove(player);
  }

  /**
   * Method for clearing the set completely.
   */
  public void clearPlayers() {
    players.clear();
  }

  /**
   * Method for getting the players as a list.
   *
   * @return A list of Player Objects, ordered.
   */
  public ArrayList<Player> getPlayersAsList() {
    return new ArrayList<>(players);
  }

  public int getPlayerCount() {
    return players.size();
  }

}
