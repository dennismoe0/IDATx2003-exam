package no.ntnu.idatx2003.exam2025.boardgames.dao;

import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.SnakesAndLaddersStatsDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.LudoStatsDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.SnakesAndLaddersStats;
import no.ntnu.idatx2003.exam2025.boardgames.service.DatabaseManager;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.LudoStats;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayerSnakeLudoImplTest {

  private Connection connection;
  private PlayerDaoImpl playerDao;
  private SnakesAndLaddersStatsDaoImpl snakesStatsDao;
  private LudoStatsDaoImpl ludoStatsDao;

  @BeforeAll
  void setupDatabase() throws SQLException {
    // Use in-memory database for testing
    connection = DriverManager.getConnection("jdbc:sqlite::memory:");
    DatabaseManager.wipeDatabase();
    DatabaseManager.initializeDatabase(connection); // Ensure tables are created

    // Initialize DAOs
    playerDao = new PlayerDaoImpl(connection);
    snakesStatsDao = new SnakesAndLaddersStatsDaoImpl(connection);
    ludoStatsDao = new LudoStatsDaoImpl(connection);

    try (Statement stmt = connection.createStatement()) {
      stmt.executeQuery("SELECT 1 FROM players LIMIT 1");
      System.out.println("Players table exists.");
    } catch (SQLException e) {
      System.err.println("Players table does not exist: " + e.getMessage());
    }
  }

  @AfterAll
  void closeConnection() throws SQLException {
    if (connection != null) {
      connection.close();
    }
  }

  @Test
  void testPlayerWithSnakesAndLaddersStats() throws SQLException {

    // Arrange

    List<Player> players = new ArrayList<>();
    players.add(new Player(0, "Dennis", 23));
    players.add(new Player(0, "Sasha", 27));

    for (Player player : players) {
      SnakesAndLaddersStats stats = new SnakesAndLaddersStats();
      int i = 0;
      stats.setWins(3 + i);
      stats.setLosses(4 + i);
      stats.setGamesPlayed(7 + i);

      int playerId = playerDao.create(player);
      player.setPlayerId(playerId);
      player.setPlayerStats(stats);
      snakesStatsDao.save(playerId, stats);

      SnakesAndLaddersStats retrievedStats = snakesStatsDao.load(playerId);

      assertEquals(3 + i, retrievedStats.getWins());
      assertEquals(4 + i, retrievedStats.getLosses());
      assertEquals(Integer.valueOf(7 + i), retrievedStats.getGamesPlayed());

      i++;
    }
  }
}
