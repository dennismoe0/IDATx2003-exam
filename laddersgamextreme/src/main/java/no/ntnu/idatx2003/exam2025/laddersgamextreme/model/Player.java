package no.ntnu.idatx2003.exam2025.laddersgamextreme.model;

/**
 * A player object that will be used to save player history such as wins, losses
 * and other stats.
 * Player id iterates by 1 for each new Player. Might have to save this number
 * in a database?
 * ^ Should delegate player creation details to the database later on.
 *
 * @author Dennis Moe
 */
public class Player {

  private int playerId;
  private String playerName;
  private int playerAge; // Apparently required
  private final PlayerStats playerStats; // Persistent stats for players

  /**
   * Constructs a new Player with the specified name.
   *
   * @param playerName the name of the player
   * @param playerAge  the age of the player
   */
  public Player(int playerId, String playerName, int playerAge) {
    this.playerId = playerId;
    this.playerName = playerName;
    this.playerStats = new PlayerStats();
    this.playerAge = playerAge;
  }

  public int getPlayerId() {
    return playerId;
  }

  public String getPlayerName() {
    return playerName;
  }

  public int getPlayerAge() {
    return playerAge;
  }

  public void setplayerAge(int playerAge) {
    this.playerAge = playerAge;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public PlayerStats getStats() {
    return playerStats;
  }
}
