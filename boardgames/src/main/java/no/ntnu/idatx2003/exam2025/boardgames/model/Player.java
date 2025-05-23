package no.ntnu.idatx2003.exam2025.boardgames.model;

import javafx.scene.paint.Color;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.PlayerStats;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

/**
 * Class representing a Player Object.
 */
public class Player {

  private static final Logger log = Log.get(Player.class);

  private int playerId;
  private String playerName;
  private PlayerStats playerStats;
  private int playerAge;
  private Color color;
  private String playingPieceAssetPath;

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
    this.color = Color.web("#00FF00"); // Injected/chosen later
  }

  /**
   * Constructor for an existing player with all properties specified.
   *
   * @param playerId    Unique ID to distinguish each player.
   * @param playerStats Player statistics object.
   * @param playerName  Non-unique String to visually represent player in-game.
   * @param playerAge   Age of the player.
   * @param color       Color associated with the player.
   */
  public Player(
      int playerId,
      PlayerStats playerStats,
      String playerName,
      int playerAge,
      Color color) {
    this.playerId = playerId;
    this.playerStats = playerStats;
    this.playerName = playerName;
    this.playerAge = playerAge;
    this.color = color;
  }

  public String getPlayerPieceAssetPath() {
    return playingPieceAssetPath;
  }

  public void setPlayerPieceAssetPath(String assetPath) {
    this.playingPieceAssetPath = assetPath;
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
