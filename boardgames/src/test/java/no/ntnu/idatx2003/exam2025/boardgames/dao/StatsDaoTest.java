package no.ntnu.idatx2003.exam2025.boardgames.dao;

import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.LudoStatsDao;
import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.LudoStatsDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.SnakesAndLaddersStatsDao;
import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.SnakesAndLaddersStatsDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.LudoStats;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.SnakesAndLaddersStats;
import no.ntnu.idatx2003.exam2025.boardgames.service.DatabaseManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;

class StatsDaoTest {
  private static final String DB_FILE = "database/laddersgamextreme.db";
  private Connection conn;

  @BeforeEach
  void setup() throws Exception {
    conn = DatabaseManager.connect();
    DatabaseManager.initializeDatabase();
  }

  @AfterEach
  void teardown() throws Exception {
    conn.close();
    Files.deleteIfExists(new File(DB_FILE).toPath());
  }

  @Test
  void ludoStatsSaveAndLoad() throws Exception {
    LudoStatsDao dao = new LudoStatsDaoImpl(conn);
    LudoStats stats = new LudoStats();
    stats.incrementMove();
    stats.incrementDiceRoll();
    stats.incrementPiecesCompleted();
    stats.incrementDoubleSixRolls();
    stats.incrementPiecesKnocked();
    stats.incrementWins();
    stats.incrementLosses();
    stats.incrementGamesPlayed();

    dao.save(1, stats);
    LudoStats loaded = dao.load(1);

    assertEquals(1, loaded.getTotalMoveCount());
    assertEquals(1, loaded.getTotalDiceRolls());
    assertEquals(1, loaded.getPiecesCompleted());
    assertEquals(1, loaded.getDoubleSixRolls());
    assertEquals(1, loaded.getPiecesKnocked());
    assertEquals(1, loaded.getWins());
    assertEquals(1, loaded.getLosses());
    assertEquals(1, loaded.getGamesPlayed());
  }

  @Test
  void snakesAndLaddersStatsSaveAndLoad() throws Exception {
    SnakesAndLaddersStatsDao dao = new SnakesAndLaddersStatsDaoImpl(conn);
    SnakesAndLaddersStats stats = new SnakesAndLaddersStats();
    stats.incrementMove();
    stats.incrementDiceRoll();
    stats.incrementLaddersUsed();
    stats.incrementSnakesUsed();
    stats.incrementWins();
    stats.incrementLosses();
    stats.incrementGamesPlayed();
    stats.setHighestDiceRoll(6);
    stats.addToSumOfAllDiceRolls(10);

    dao.save(2, stats);
    SnakesAndLaddersStats loaded = dao.load(2);

    assertEquals(1, loaded.getTotalMoveCount());
    assertEquals(1, loaded.getTotalDiceRolls());
    assertEquals(1, loaded.getLaddersUsed());
    assertEquals(1, loaded.getSnakesUsed());
    assertEquals(1, loaded.getWins());
    assertEquals(1, loaded.getLosses());
    assertEquals(1, loaded.getGamesPlayed());
    assertEquals(6, loaded.getHighestDiceRoll());
    assertEquals(10, loaded.getSumOfAllDiceRolls());
  }
}
