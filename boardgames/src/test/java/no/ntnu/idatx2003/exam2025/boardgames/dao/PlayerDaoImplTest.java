package no.ntnu.idatx2003.exam2025.boardgames.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.service.DatabaseManager;

/**
 * Integration tests for PlayerDaoImpl using an in-memory SQLite database.
 */
public class PlayerDaoImplTest {

  private Connection connection;
  private PlayerDaoImpl playerDao;

  @BeforeEach
  void setUp() throws SQLException {
    // Use in-memory SQLite for isolation
    connection = DriverManager.getConnection("jdbc:sqlite::memory:");
    DatabaseManager.initializeDatabase(connection);
    playerDao = new PlayerDaoImpl(connection);
  }

  @AfterEach
  void tearDown() throws SQLException {
    connection.close();
  }

  @Test
  void testCreateAndFindById() throws SQLException {
    Player original = new Player(0, "Alice", 21);
    int id = playerDao.create(original);
    assertTrue(id > 0, "Generated ID should be positive");

    Player fetched = playerDao.findById(id);
    assertNotNull(fetched, "findById should return a Player for existing ID");
    assertEquals(id, fetched.getPlayerId());
    assertEquals("Alice", fetched.getPlayerName());
    assertEquals(21, fetched.getPlayerAge());
    assertNull(fetched.getPlayerStats(), "PlayerStats should be null by default");
  }

  @Test
  void testFindByIdNotExists() throws SQLException {
    Player missing = playerDao.findById(9999);
    assertNull(missing, "findById should return null for non-existing ID");
  }

  @Test
  void testDeleteRemovesPlayer() throws SQLException {
    Player p1 = new Player(0, "Bob", 30);
    Player p2 = new Player(0, "Carol", 25);
    int id1 = playerDao.create(p1);
    int id2 = playerDao.create(p2);

    // Verify both exist
    assertNotNull(playerDao.findById(id1));
    assertNotNull(playerDao.findById(id2));

    // Delete first
    playerDao.delete(id1);
    assertNull(playerDao.findById(id1), "Deleted player should not be found");
    assertNotNull(playerDao.findById(id2), "Other player should still exist");
  }

  @Test
  void testDeleteNonExistingDoesNotThrow() {
    assertDoesNotThrow(() -> playerDao.delete(12345),
        "Deleting non-existing ID should not throw an exception");
  }

  @Test
  void testGetAllPlayerNamesAndIds() throws SQLException {
    // Create multiple players
    Player a = new Player(0, "Dennis", 40);
    Player b = new Player(0, "Sasha", 35);
    Player c = new Player(0, "Turid", 28);
    int id1 = playerDao.create(a);
    int id2 = playerDao.create(b);
    int id3 = playerDao.create(c);

    List<String> names = playerDao.getAllPlayerNames();
    List<Integer> ids = playerDao.getAllPlayerIds();

    // Should contain exactly the three names/IDs
    assertEquals(List.of("Dennis", "Sasha", "Turid"), names);
    assertEquals(List.of(id1, id2, id3), ids);
  }

  @Test
  void testGetAllWhenEmpty() throws SQLException {
    // No players
    List<String> names = playerDao.getAllPlayerNames();
    List<Integer> ids = playerDao.getAllPlayerIds();
    assertTrue(names.isEmpty(), "getAllPlayerNames should return empty when no players");
    assertTrue(ids.isEmpty(), "getAllPlayerIds should return empty when no players");
  }
}