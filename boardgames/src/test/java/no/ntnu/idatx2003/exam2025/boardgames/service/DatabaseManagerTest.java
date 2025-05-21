package no.ntnu.idatx2003.exam2025.boardgames.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DatabaseManagerTest {

  private Connection connection;
  private static final String DB_DIR = "database";
  private static final String DB_PATH = DB_DIR + "/boardgames.db";

  @BeforeEach
  void setUp() throws SQLException {
    // ensure a clean slate
    File dbFile = new File(DB_PATH);
    if (dbFile.exists()) {
      dbFile.delete();
    }

    connection = DatabaseManager.connect();
    DatabaseManager.initializeDatabase(connection);
  }

  @AfterEach
  void tearDown() {
    DatabaseManager.closeConnection(connection);
    // clean up the file
    new File(DB_PATH).delete();
  }

  @Test
  void testConnectReturnsValidConnection() throws SQLException {
    assertNotNull(connection, "Connection should not be null");
    assertFalse(connection.isClosed(), "Connection should be open");
  }

  @Test
  void testTablesAreCreated() throws SQLException {
    DatabaseMetaData metaData = connection.getMetaData();

    try (ResultSet rs = metaData.getTables(null, null, "players", null)) {
      assertTrue(rs.next(), "Table 'players' should exist");
    }
    try (ResultSet rs = metaData.getTables(null, null, "snakes_and_ladders_stats", null)) {
      assertTrue(rs.next(), "Table 'snakes_and_ladders_stats' should exist");
    }
    try (ResultSet rs = metaData.getTables(null, null, "ludo_stats", null)) {
      assertTrue(rs.next(), "Table 'ludo_stats' should exist");
    }
  }

  @Test
  void testWipeDatabaseDropsTables() throws SQLException {
    // first ensure tables exist
    testTablesAreCreated();

    // now wipe
    DatabaseManager.wipeDatabase();

    DatabaseMetaData metaData = connection.getMetaData();

    try (ResultSet rs = metaData.getTables(null, null, "players", null)) {
      assertFalse(rs.next(), "Table 'players' should not exist after wipe");
    }
    try (ResultSet rs = metaData.getTables(null, null, "snakes_and_ladders_stats", null)) {
      assertFalse(rs.next(), "Table 'snakes_and_ladders_stats' should not exist after wipe");
    }
    try (ResultSet rs = metaData.getTables(null, null, "ludo_stats", null)) {
      assertFalse(rs.next(), "Table 'ludo_stats' should not exist after wipe");
    }
  }
}
