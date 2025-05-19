package no.ntnu.idatx2003.exam2025.boardgames.model;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.PlayerStats;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

import javafx.scene.paint.Color;

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

  // // TO BE REMOVED / MOVED TO GameSession

  // /**
  // * Adds a game piece to the player's collection of game pieces.
  // *
  // * @param gamePiece The game piece to be added to the player.
  // */
  // public void addGamePieceToPlayer(GamePiece gamePiece) {
  // gamePieces.add(gamePiece);
  // log.debug("Added a GamePiece to player {}", playerName);
  // }

  // /**
  // * Removes a game piece from the player's collection of game pieces.
  // *
  // * @param gamePiece The game piece to be removed from the player.
  // */
  // public void removeGamePieceFromPlayer(GamePiece gamePiece) {
  // gamePieces.remove(gamePiece);
  // log.debug("Removed a GamePiece from player {}.", playerName);
  // }

  // public List<GamePiece> getGamePieces() {
  // if (gamePieces.isEmpty()) {
  // log.debug("There are no Game Pieces assigned to {}", playerName);
  // return gamePieces;
  // }
  // return gamePieces;
  // }

  // /**
  // * Checks if the player has any game pieces assigned.
  // *
  // * @return true if the player has one or more game pieces, false otherwise.
  // */
  // public boolean playerHasGamePieces() {
  // return !gamePieces.isEmpty();
  // }

  // public GamePiece getGamePiece(int index) {
  // if (!playerHasGamePieces()) {
  // throw new IllegalArgumentException("Player (" + playerName + ") has no Game
  // Pieces assigned.");
  // }
  // if (index < 0 || index >= gamePieces.size()) {
  // throw new IndexOutOfBoundsException("No game piece at index: " + index +
  // ".");
  // }
  // return gamePieces.get(index);
  // }

  // public void clearAllGamePieces() {
  // gamePieces.clear();
  // log.debug("Removed all gamePieces from {}", playerName);
  // }

}
