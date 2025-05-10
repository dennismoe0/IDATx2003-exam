package no.ntnu.idatx2003.exam2025.boardgames.service;

import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDao;
import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.LudoStatsDao;
import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.SnakesAndLaddersStatsDao;

public class StatsManager {
  private final PlayerDao playerDao;

  private final BoardGame

  public StatsManager(PlayerDao playerDao) {
    this.playerDao = playerDao;
  }
}
