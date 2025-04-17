package no.ntnu.idatx2003.exam2025.boardgames.service;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manages the SQLite database connection and ensures tables exist.
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
    createDatabaseFileIfNotExists();
    Connection connection = DriverManager.getConnection(DB_URL);

    // Configure connection for concurrency
    try (Statement stmt = connection.createStatement()) {
      stmt.execute("PRAGMA journal_mode=WAL");
      stmt.execute("PRAGMA busy_timeout=5000");
    }

    return connection;
  }

  /**
   * Initializes the database and ensures required tables exist.
   */
  public static void initializeDatabase() {
    // Ensure the 'database' directory exists
    new File("database").mkdirs();

    try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
      // Players table
      String createPlayersTable = """
          CREATE TABLE IF NOT EXISTS players (
            player_id INTEGER PRIMARY KEY AUTOINCREMENT,
            player_name TEXT NOT NULL,
            player_age INTEGER NOT NULL
          );
          """;

      // Snakes and Ladders stats table
      String createSnakesAndLaddersStatsTable = """
          CREATE TABLE IF NOT EXISTS snakes_and_ladders_stats (
            stats_id INTEGER PRIMARY KEY AUTOINCREMENT,
            player_id INTEGER NOT NULL,
            wins INTEGER DEFAULT 0,
            losses INTEGER DEFAULT 0,
            games_played INTEGER DEFAULT 0,
            ladders_used INTEGER DEFAULT 0,
            snakes_used INTEGER DEFAULT 0,
            highest_dice_roll INTEGER DEFAULT 0,
            total_dice_rolls INTEGER DEFAULT 0,
            sum_of_all_dice_rolls INTEGER DEFAULT 0,
            FOREIGN KEY (player_id) REFERENCES players(player_id) ON DELETE CASCADE
          );
          """;

      // Chess stats table
      String createChessStatsTable = """
          CREATE TABLE IF NOT EXISTS chess_stats (
            stats_id INTEGER PRIMARY KEY AUTOINCREMENT,
            player_id INTEGER NOT NULL,
            wins INTEGER DEFAULT 0,
            losses INTEGER DEFAULT 0,
            games_played INTEGER DEFAULT 0,
            checkmates INTEGER DEFAULT 0,
            stalemates INTEGER DEFAULT 0,
            pieces_captured INTEGER DEFAULT 0,
            total_moves INTEGER DEFAULT 0,
            FOREIGN KEY (player_id) REFERENCES players(player_id) ON DELETE CASCADE
          );
          """;

      // Ludo stats table
      String createLudoStatsTable = """
          CREATE TABLE IF NOT EXISTS ludo_stats (
            stats_id INTEGER PRIMARY KEY AUTOINCREMENT,
            player_id INTEGER NOT NULL,
            wins INTEGER DEFAULT 0,
            losses INTEGER DEFAULT 0,
            games_played INTEGER DEFAULT 0,
            pieces_completed INTEGER DEFAULT 0,
            six_rolls INTEGER DEFAULT 0,
            pieces_knocked INTEGER DEFAULT 0,
            FOREIGN KEY (player_id) REFERENCES players(player_id) ON DELETE CASCADE
          );
          """;

      // Execute table creation
      stmt.execute(createPlayersTable);
      stmt.execute(createSnakesAndLaddersStatsTable);
      // stmt.execute(createChessStatsTable);
      // stmt.execute(createLudoStatsTable);

      // Create indexes for faster lookups
      stmt.execute("CREATE INDEX IF NOT EXISTS idx_snl_player_id ON snakes_and_ladders_stats(player_id)");
      // stmt.execute("CREATE INDEX IF NOT EXISTS idx_chess_player_id ON
      // chess_stats(player_id)");
      // stmt.execute("CREATE INDEX IF NOT EXISTS idx_ludo_player_id ON
      // ludo_stats(player_id)");

      System.out.println("Database initialized successfully.");
    } catch (SQLException e) {
      System.err.println("Database initialization failed: " + e.getMessage());
    }
  }
}