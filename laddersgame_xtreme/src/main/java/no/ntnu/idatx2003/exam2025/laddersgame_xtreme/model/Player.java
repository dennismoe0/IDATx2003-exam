package no.ntnu.idatx2003.exam2025.laddersgame_xtreme.model;

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

  private static int playerIdCounter = 1;

  private final int playerId;
  private String playerName;

  /**
   * Constructs a new Player with the specified name.
   *
   * @param playerName the name of the player
   */
  public Player(String playerName) {
    this.playerId = playerIdCounter++;
    this.playerName = playerName;
  }

  public int getPlayerId() {
    return playerId;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }
}
