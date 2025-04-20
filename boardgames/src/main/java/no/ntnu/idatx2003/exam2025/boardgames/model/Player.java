package no.ntnu.idatx2003.exam2025.boardgames.model;

import no.ntnu.idatx2003.exam2025.boardgames.model.stats.DefaultPlayerStats;

public class Player {

  // Constructor should have id, name, stats?
  private int playerId;
  private String playerName;
  private PlayerStats playerStats;

  // new player constructor
  public Player(int playerId, String playerName) {
    this.playerId = playerId;
    this.playerName = playerName;
    this.playerStats = new DefaultPlayerStats(playerName);
  }

  // Existing player from database constructor

  public Player(int playerId, PlayerStats stats) {
    this.playerId = playerId;
    this.playerStats = playerStats;
  }
}
