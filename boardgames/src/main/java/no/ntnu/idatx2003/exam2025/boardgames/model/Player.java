package no.ntnu.idatx2003.exam2025.boardgames.model;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.PlayerStats;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.DefaultPlayerStats;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

public class Player {

  private static final Logger log = Log.get(Dice.class);

  private final int playerId;
  private String playerName;
  private final PlayerStats playerStats;
  private final List<GamePiece> gamePieces; // can be null

  /**
   * Constructor for a NEW player.
   * Needs ID (generatd by DB/DAO?) and a name for the profile.
   *
   * @param playerId   Unique ID to distinguish each player.
   * @param playerName Non-unique String to visually represent player in-game.
   */
  public Player(int playerId, String playerName) {
    this.playerId = playerId;
    this.playerName = playerName;
    this.playerStats = new DefaultPlayerStats(playerName);
    this.gamePieces = new ArrayList<>();
  }

  /**
   * Existing player from database constructor, this will be loaded in.
   *
   * @param playerId    Unique identifier INT for each player. Used to identify
   *                    player to load.
   * @param playerStats Unique stats connected to player-profile.
   */
  public Player(int playerId, PlayerStats playerStats) {
    this.playerId = playerId;
    this.playerStats = playerStats;
    this.gamePieces = new ArrayList<>();
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

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  /**
   * Assigns a game piece to the player.
   *
   * @param gamePiece The game piece to be assigned to the player.
   */
  public void assignGamePieceToPlayer(GamePiece gamePiece) {
    this.gamePiece = gamePiece;
  }

  /**
   * Checks if the player has been assigned a game piece.
   *
   * @return true if the player has a game piece assigned, false otherwise.
   */
  public boolean playerHasGamePiece() {
    return gamePiece != null;
  }

  public GamePiece getGamePiece() {
    if (!playerHasGamePiece()) {
      throw new IllegalArgumentException("Player has no gamePiece assigned");
    }
    return gamePiece;
  }

}
