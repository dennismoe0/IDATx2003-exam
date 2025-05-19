package no.ntnu.idatx2003.exam2025.boardgames.model;

import javafx.scene.paint.Color;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.PlayerStats;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

/**
 * Player object represents a person playing the game.
 * It keeps track of stats/persistence and is used throughout the
 * life cycle of the program.
 */
public class Player {

  private static final Logger log = Log.get(Player.class);

  private int playerId;
  private String playerName;
  private PlayerStats playerStats;
  private int playerAge;
  private Color color;

  /**
   * Constructor for a NEW player.
   * Needs ID (generatd by DB/DAO?) and a name for the profile.
   *
   * @param playerId   Unique ID to distinguish each player.
   * @param playerName Non-unique String to visually represent player in-game.
   */
  public Player(int playerId, String playerName, int playerAge) {
    this.playerId = playerId;
    this.playerStats = null; // Injected later
    this.playerName = playerName;
    this.playerAge = playerAge;
    this.color = Color.GREEN; // Injected/chosen later
  }

  /**
   * Existing player from database constructor, this will be loaded in.
   *
   * @param playerId    Unique identifier INT for each player. Used to identify
   *                    player to load.
   * @param playerStats Unique stats connected to player-profile.
   */
  public Player(int playerId, PlayerStats playerStats, String playerName, int playerAge) {
    this.playerId = playerId;
    this.playerStats = playerStats;
    this.playerName = playerName;
    this.playerAge = playerAge;
  }

  public int getPlayerId() {
    return playerId;
  }

  public String getPlayerName() {
    return playerName;
  }

  public PlayerStats getPlayerStats() {
    return playerStats;
  }

  public int getPlayerAge() {
    return playerAge;
  }

  public Color getPlayerColor() {
    return color;
  }

  public void setPlayerColor(Color color) {
    this.color = color;
  }

  public void setPlayerId(int playerId) {
    this.playerId = playerId;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public void setPlayerAge(int playerAge) {
    this.playerAge = playerAge;
  }

  public void setPlayerStats(PlayerStats playerStats) {
    this.playerStats = playerStats;
  }

}
