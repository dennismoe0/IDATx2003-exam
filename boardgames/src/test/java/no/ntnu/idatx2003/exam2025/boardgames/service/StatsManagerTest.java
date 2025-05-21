package no.ntnu.idatx2003.exam2025.boardgames.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.GameStatsDao;
import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.LudoStatsDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.SnakesAndLaddersStatsDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.PlayerStats;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.LudoStats;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.SnakesAndLaddersStats;

/**
 * Unit tests for StatsManager.
 */
class StatsManagerTest {
  private Connection connection;
  private PlayerDaoImpl playerDao;
  private StatsManager<SnakesAndLaddersStats> snlManager;
  private StatsManager<LudoStats> ludoManager;

  @BeforeEach
  void setUp() throws SQLException {
    connection = DriverManager.getConnection("jdbc:sqlite::memory:");
    DatabaseManager.initializeDatabase(connection);
    playerDao = new PlayerDaoImpl(connection);
    SnakesAndLaddersStatsDaoImpl snlDao = new SnakesAndLaddersStatsDaoImpl(connection);
    LudoStatsDaoImpl ludoDao = new LudoStatsDaoImpl(connection);

    snlManager = new StatsManager<>(playerDao, snlDao);
    ludoManager = new StatsManager<>(playerDao, ludoDao);
  }

  @AfterEach
  void tearDown() throws SQLException {
    connection.close();
  }

  @Test
  void testSnakesAndLaddersStatsPersistsAndLoadsCorrectly() throws SQLException {
    Player dennis = new Player(0, "Dennis", 25);
    dennis.setPlayerId(playerDao.create(dennis));
    SnakesAndLaddersStats stats = new SnakesAndLaddersStats();
    stats.incrementWins();
    stats.incrementLosses();
    stats.incrementLaddersUsed();
    stats.incrementSnakesUsed();
    stats.setHighestDiceRoll(6);
    stats.incrementDiceRoll();
    stats.incrementDiceRoll();
    stats.incrementMove(4);
    stats.incrementMove(2);

    snlManager.save(dennis, stats);
    SnakesAndLaddersStats loaded = snlManager.loadStats(dennis);

    assertEquals(1, loaded.getWins());
    assertEquals(1, loaded.getLosses());
    assertEquals(2, loaded.getGamesPlayed());
    assertEquals(1, loaded.getLaddersUsed());
    assertEquals(1, loaded.getSnakesUsed());
    assertEquals(6, loaded.getHighestDiceRoll());
    assertEquals(2, loaded.getTotalDiceRolls());
    assertEquals(6, loaded.getTotalMoveCount());

    List<Integer> vals = snlManager.loadAllStats(dennis);
    assertEquals(8, vals.size());
    assertEquals(1, vals.get(0));
    assertEquals(6, vals.get(5));
    assertEquals(6, snlManager.loadStat(dennis, 8));

    LinkedHashMap<String, Integer> map = snlManager.loadStatsAsMap(dennis);
    assertEquals(1, map.get("wins"));
    assertEquals(6, map.get("highestDiceRoll"));
    assertEquals(6, map.get("totalMoves"));
  }

  @Test
  void testLudoStatsPersistsAndLoadsCorrectly() throws SQLException {
    Player sasha = new Player(0, "Sasha", 30);
    sasha.setPlayerId(playerDao.create(sasha));
    LudoStats stats = new LudoStats();
    stats.incrementWins();
    stats.incrementLosses();
    stats.incrementPiecesCompleted();
    stats.incrementDoubleSixRolls();
    stats.incrementPiecesKnocked();
    stats.incrementDiceRoll();
    stats.incrementDiceRoll();
    stats.incrementMove(6);

    ludoManager.save(sasha, stats);
    LudoStats loaded = ludoManager.loadStats(sasha);

    assertEquals(1, loaded.getWins());
    assertEquals(1, loaded.getLosses());
    assertEquals(2, loaded.getGamesPlayed());
    assertEquals(1, loaded.getPiecesCompleted());
    assertEquals(1, loaded.getDoubleSixRolls());
    assertEquals(1, loaded.getPiecesKnocked());
    assertEquals(2, loaded.getTotalDiceRolls());
    assertEquals(6, loaded.getTotalMoveCount());

    List<Integer> vals = ludoManager.loadAllStats(sasha);
    assertEquals(8, vals.size());
    assertEquals(1, vals.get(0));
    assertEquals(2, vals.get(7));
    assertEquals(6, ludoManager.loadStat(sasha, 7));

    Map<String, Integer> map = ludoManager.loadStatsAsMap(sasha);
    assertEquals(1, map.get("piecesCompleted"));
    assertEquals(2, map.get("totalDiceRolls"));
    assertEquals(6, map.get("totalMoves"));
  }

  @Test
  void testCreatePersistedPlayerWithSnakesAndLaddersStats() throws Exception {
    Player turid = new Player(0, "Turid", 20);
    Player decimus = new Player(0, "Decimus Maximus Brutus", 35);
    List<Player> list = List.of(turid, decimus);
    snlManager.createPersistedPlayerWithStats(list, SnakesAndLaddersStats.class);
    for (Player p : list) {
      assertTrue(p.getPlayerId() > 0);
      assertNotNull(p.getPlayerStats());
      List<Integer> vals = snlManager.loadAllStats(p);
      assertEquals(8, vals.size());
      vals.forEach(v -> assertEquals(0, v));
      assertEquals(0, snlManager.loadStat(p, 1));
      assertEquals(0, snlManager.loadStat(p, 8));
    }
  }

  @Test
  void testSaveWithInvalidPlayer() {
    Player invalid = new Player(0, "TestPerson2", 50);
    SnakesAndLaddersStats stats = new SnakesAndLaddersStats();
    assertThrows(IllegalArgumentException.class, () -> snlManager.save(invalid, stats));
  }

  @Test
  void testLoadStatIndexBoundaries() throws SQLException {
    Player dennis = new Player(0, "Dennis", 25);
    dennis.setPlayerId(playerDao.create(dennis));
    SnakesAndLaddersStats stats = new SnakesAndLaddersStats();
    snlManager.save(dennis, stats);
    assertEquals(0, snlManager.loadStat(dennis, 1));
    assertEquals(0, snlManager.loadStat(dennis, 8));
  }

  @Test
  void testLoadStatsAsMapMismatch() throws SQLException {
    Player dummy = new Player(0, "TestPerson3", 60);
    dummy.setPlayerId(playerDao.create(dummy));
    PlayerStats bad = new PlayerStats() {
      @Override
      public List<String> getStatNames() {
        return List.of("a", "b", "c");
      }

      @Override
      public List<Integer> getStatValues() {
        return List.of(1, 2);
      }
    };
    GameStatsDao<PlayerStats> badDao = new GameStatsDao<>() {
      @Override
      public void save(int pid, PlayerStats s) {
      }

      @Override
      public PlayerStats load(int pid) {
        return bad;
      }
    };
    StatsManager<PlayerStats> mgr = new StatsManager<>(playerDao, badDao);
    assertThrows(IllegalStateException.class, () -> mgr.loadStatsAsMap(dummy));
  }

  @Test
  void testLoadStatIndexOutOfRange() throws SQLException {
    Player test1 = new Player(0, "TestPerson4", 28);
    test1.setPlayerId(playerDao.create(test1));
    SnakesAndLaddersStats stats = new SnakesAndLaddersStats();
    snlManager.save(test1, stats);
    assertThrows(IllegalArgumentException.class, () -> snlManager.loadStat(test1, 0));
    assertThrows(IllegalArgumentException.class, () -> snlManager.loadStat(test1, 9));
  }

  @Test
  void testLoadStatsWithInvalidPlayer() {
    Player test5 = new Player(-1, "TestPerson5", 100);
    assertThrows(IllegalArgumentException.class, () -> snlManager.loadStats(test5));
  }
}
