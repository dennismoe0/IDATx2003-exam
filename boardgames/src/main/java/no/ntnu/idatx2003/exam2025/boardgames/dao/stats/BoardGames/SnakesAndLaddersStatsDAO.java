package no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames;

import java.sql.SQLException;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.SnakesAndLaddersStats;

public interface SnakesAndLaddersStatsDao {
  void save(int playerId, SnakesAndLaddersStats stats) throws SQLException;

  SnakesAndLaddersStats load(int playerId) throws SQLException;
}