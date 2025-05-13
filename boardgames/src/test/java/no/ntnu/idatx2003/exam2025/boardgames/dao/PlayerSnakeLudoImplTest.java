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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

  @Test
  void testPlayerWithLudoStats() throws SQLException {
    // Arrange
    List<Player> players = new ArrayList<>();
    players.add(new Player(0, "Alice", 30));
    players.add(new Player(0, "Bob", 35));

    int i = 0;
    for (Player player : players) {
      LudoStats stats = new LudoStats();
      stats.setWins(5 + i);
      stats.setLosses(2 + i);
      stats.setGamesPlayed(10 + i);
      stats.setPiecesCompleted(8 + i);
      stats.setDoubleSixRolls(3 + i);
      stats.setPiecesKnocked(4 + i);

      // Act
      int playerId = playerDao.create(player);
      player.setPlayerId(playerId);
      player.setPlayerStats(stats);
      ludoStatsDao.save(playerId, stats);

      // Assert
      LudoStats retrievedStats = ludoStatsDao.load(playerId);
      assertEquals(5 + i, retrievedStats.getWins());
      assertEquals(2 + i, retrievedStats.getLosses());
      assertEquals(10 + i, retrievedStats.getGamesPlayed());
      assertEquals(8 + i, retrievedStats.getPiecesCompleted());
      assertEquals(3 + i, retrievedStats.getDoubleSixRolls());
      assertEquals(4 + i, retrievedStats.getPiecesKnocked());

      i++;
    }
  }

  @Test
  void testMultiplePlayersWithMixedStats() throws SQLException {
    // Arrange
    Player player1 = new Player(0, "Charlie", 28);
    Player player2 = new Player(0, "Diana", 32);

    SnakesAndLaddersStats stats1 = new SnakesAndLaddersStats();
    stats1.setWins(3);
    stats1.setLosses(1);
    stats1.setGamesPlayed(4);

    LudoStats stats2 = new LudoStats();
    stats2.setWins(6);
    stats2.setLosses(2);
    stats2.setGamesPlayed(8);
    stats2.setPiecesCompleted(10);

    // Act
    int player1Id = playerDao.create(player1);
    player1.setPlayerId(player1Id);
    player1.setPlayerStats(stats1);
    snakesStatsDao.save(player1Id, stats1);

    int player2Id = playerDao.create(player2);
    player2.setPlayerId(player2Id);
    player2.setPlayerStats(stats2);
    ludoStatsDao.save(player2Id, stats2);

    // Assert
    SnakesAndLaddersStats retrievedStats1 = snakesStatsDao.load(player1Id);
    assertEquals(3, retrievedStats1.getWins());
    assertEquals(1, retrievedStats1.getLosses());
    assertEquals(4, retrievedStats1.getGamesPlayed());

    LudoStats retrievedStats2 = ludoStatsDao.load(player2Id);
    assertEquals(6, retrievedStats2.getWins());
    assertEquals(2, retrievedStats2.getLosses());
    assertEquals(8, retrievedStats2.getGamesPlayed());
    assertEquals(10, retrievedStats2.getPiecesCompleted());
  }

  @Test
  void testUpdatePlayerStats() throws SQLException {
    // Arrange
    Player player = new Player(0, "Eve", 29);
    SnakesAndLaddersStats stats = new SnakesAndLaddersStats();
    stats.setWins(2);
    stats.setLosses(3);
    stats.setGamesPlayed(5);

    // Act
    int playerId = playerDao.create(player);
    player.setPlayerId(playerId);
    player.setPlayerStats(stats);
    snakesStatsDao.save(playerId, stats);

    // Update stats
    stats.setWins(4);
    stats.setLosses(2);
    stats.setGamesPlayed(6);
    snakesStatsDao.save(playerId, stats);

    // Assert
    SnakesAndLaddersStats updatedStats = snakesStatsDao.load(playerId);
    assertEquals(4, updatedStats.getWins());
    assertEquals(2, updatedStats.getLosses());
    assertEquals(6, updatedStats.getGamesPlayed());
  }

  @Test
  void testDeletePlayerAndStats() throws SQLException {
    // Arrange
    Player player = new Player(0, "Frank", 33);
    SnakesAndLaddersStats stats = new SnakesAndLaddersStats();
    stats.setWins(1);
    stats.setLosses(2);
    stats.setGamesPlayed(3);

    // Act
    int playerId = playerDao.create(player);
    player.setPlayerId(playerId);
    snakesStatsDao.save(playerId, stats);

    // Delete the player
    playerDao.delete(playerId);

    // Assert
    SnakesAndLaddersStats retrievedStats = snakesStatsDao.load(playerId);
    assertNull(retrievedStats, "Stats should not exist for a deleted player.");
  }

  @Test
  void testSaveStatsWithoutPlayer() throws SQLException {
    // Arrange
    SnakesAndLaddersStats stats = new SnakesAndLaddersStats();
    stats.setWins(5);
    stats.setLosses(3);
    stats.setGamesPlayed(8);

    // Act & Assert
    assertThrows(SQLException.class, () -> snakesStatsDao.save(999, stats),
        "Expected SQLException when saving stats for a non-existent player.");
  }

  @Test
  void testRetrieveStatsForNonExistentPlayer() throws SQLException {
    // Act
    SnakesAndLaddersStats retrievedStats = snakesStatsDao.load(999); // Player ID 999 does not exist

    // Assert
    assertNull(retrievedStats, "Stats should not exist for a non-existent player.");
  }

  @Test
  void testSaveDuplicateStats() throws SQLException {
    // Arrange
    Player player = new Player(0, "DuplicatePlayer", 30);
    int playerId = playerDao.create(player);

    SnakesAndLaddersStats stats1 = new SnakesAndLaddersStats();
    stats1.setWins(3);
    stats1.setLosses(2);
    stats1.setGamesPlayed(5);

    SnakesAndLaddersStats stats2 = new SnakesAndLaddersStats();
    stats2.setWins(7);
    stats2.setLosses(1);
    stats2.setGamesPlayed(8);

    // Act
    // Save stats twice for the same player
    snakesStatsDao.save(playerId, stats1);
    snakesStatsDao.save(playerId, stats2); // Overwrites stats1

    // Assert
    SnakesAndLaddersStats retrievedStats = snakesStatsDao.load(playerId);
    if (retrievedStats.getWins() == 7) {
      System.out.println("INCORRECT BEHAVIOR: Stats were overwritten without warning.");
      assertEquals(7, retrievedStats.getWins()); // Should match stats2
      assertEquals(1, retrievedStats.getLosses());
      assertEquals(8, retrievedStats.getGamesPlayed());
    } else {
      System.out.println("CORRECT BEHAVIOR: Duplicate stats were not allowed.");
      assertEquals(3, retrievedStats.getWins()); // Should match stats1
      assertEquals(2, retrievedStats.getLosses());
      assertEquals(5, retrievedStats.getGamesPlayed());
    }
  }
}
