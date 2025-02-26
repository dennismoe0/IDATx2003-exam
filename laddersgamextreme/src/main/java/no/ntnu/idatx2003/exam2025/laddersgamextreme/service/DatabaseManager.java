package no.ntnu.idatx2003.exam2025.laddersgamextreme.service;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manages the SQLite databse connectio nand ensures tables exist.
 */
public class DatabaseManager {

  private static final String DB_PATH = "database/laddersgamextreme.db";
  private static final String DB_URL = "jdbc:sqlite:" + DB_PATH;

  static {
    try {
      Class.forName("org.sqlite.JDBC"); // Ensure SQLite driver is registered
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("Failed to load SQLite JDBC driver", e);
    }
  }

  /**
   * Ensures the database file exists before connecting.
   */
  private static void createDatabaseFileIfNotExists() {
    File dbFile = new File(DB_PATH);
    if (!dbFile.exists()) {
      try {
        new File("database").mkdirs(); // Ensure directory exists
        if (dbFile.createNewFile()) {
          System.out.println("Database file created: " + DB_PATH);
        }
      } catch (Exception e) {
        System.err.println("Failed to create database file: " + e.getMessage());
      }
    }
  }

  /**
   * Connects to the SQLite database.
   *
   * @return Connection object
   * @throws SQLException if connection fails.
   */
  public static Connection connect() throws SQLException {
    createDatabaseFileIfNotExists(); // Ensure DB file exists before connecting
    return DriverManager.getConnection(DB_URL);
  }

  /**
   * Initializes the database and ensures required tables exist.
   */
  public static void initializeDatabase() {
    // Ensure the 'database' directory exists
    new File("database").mkdirs();

    try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
      // Players table
      // ID, name and age.
      String createPlayersTable = """
          CREATE TABLE IF NOT EXISTS players (
            player_id INTEGER PRIMARY KEY AUTOINCREMENT,
            player_name TEXT NOT NULL,
            player_age INTEGER NOT NULL
          );
          """;

      // PlayerStats table (linked to Players)
      // ID, wins, losses, other stats. Can be expanded.
      String createPlayerStatsTable = """
          CREATE TABLE IF NOT EXISTS player_stats (
            player_id INTEGER PRIMARY KEY,
            player_wins INTEGER DEFAULT 0,
            player_losses INTEGER DEFAULT 0,
            ladders_used INTEGER DEFAULT 0,
            snakes_used INTEGER DEFAULT 0,
            FOREIGN KEY (player_id) REFERENCES players(player_id) ON DELETE CASCADE
          );
          """;

      // Execute table creation
      stmt.execute(createPlayersTable);
      stmt.execute(createPlayerStatsTable);
      System.out.println("Database initialized successfully.");
    } catch (SQLException e) {
      System.err.println("Database initialization failed: " + e.getMessage());
    }
  }
}
