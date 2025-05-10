package no.ntnu.idatx2003.exam2025.boardgames.service;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

/**
 * Manages the SQLite database connection and ensures tables exist.
 */
public class DatabaseManager {

  private static final Logger log = Log.get(DatabaseManager.class);

  private static final String DB_PATH = "database/boardgames.db";
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
        log.info("Database file does not exist. Creating a new one...");
        new File("database").mkdirs(); // Ensure directory exists
        if (dbFile.createNewFile()) {
          log.info("Database file created: " + DB_PATH);
        }
      } catch (SecurityException | IOException e) {
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
      stmt.execute("PRAGMA foreign_keys=ON");
    }

    return connection;
  }

  public static void wipeDatabase() {
    new File("database").mkdirs();

    try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
      String dropPlayersTable = "DROP TABLE IF EXISTS players;";
      String dropSnakesAndLaddersStatsTable = "DROP TABLE IF EXISTS snakes_and_ladders_stats;";
      String dropLudoStatsTable = "DROP TABLE IF EXISTS ludo_stats;";

      stmt.execute(dropSnakesAndLaddersStatsTable);
      log.debug(" Attempted to drop snakes and ladder tables.");
      stmt.execute(dropLudoStatsTable);
      log.debug("Attempted to drop ludo tables.");
      stmt.execute(dropPlayersTable);
      log.debug("Attempted to drop player tables.");
    } catch (SQLException e) {
      System.err.println("Database wiping failed: " + e.getMessage());
    }
  }

  /**
   * Initializes the database and ensures required tables exist.
   */
  public static void initializeDatabase(Connection connection) {
    // Ensure the 'database' directory exists
    new File("database").mkdirs();

    try (Statement stmt = connection.createStatement()) {
      // Players table
      String createPlayersTable = """
          CREATE TABLE IF NOT EXISTS players (
            player_id INTEGER PRIMARY KEY AUTOINCREMENT,
            player_name TEXT NOT NULL,
            player_age INTEGER NOT NULL
          );
          """;
      log.debug("Created Players table.");

      // Snakes and Ladders stats table
      String createSnakesAndLaddersStatsTable = """
            CREATE TABLE IF NOT EXISTS snakes_and_ladders_stats (
            stats_id INTEGER PRIMARY KEY AUTOINCREMENT,
            player_id INTEGER NOT NULL UNIQUE,
            wins INTEGER DEFAULT 0,
            losses INTEGER DEFAULT 0,
            games_played INTEGER DEFAULT 0,
            ladders_used INTEGER DEFAULT 0,
            snakes_used INTEGER DEFAULT 0,
            highest_dice_roll INTEGER DEFAULT 0,
            total_dice_rolls INTEGER DEFAULT 0,
            sum_of_all_dice_rolls INTEGER DEFAULT 0,
            total_moves INTEGER DEFAULT 0,
            FOREIGN KEY (player_id) REFERENCES players(player_id) ON DELETE CASCADE
          );
              """;
      log.debug("Created Snakes & Ladders stat table.");

      // Ludo stats table
      String createLudoStatsTable = """
                CREATE TABLE IF NOT EXISTS ludo_stats (
                stats_id INTEGER PRIMARY KEY AUTOINCREMENT,
                player_id INTEGER NOT NULL UNIQUE,
                wins INTEGER DEFAULT 0,
                losses INTEGER DEFAULT 0,
                games_played INTEGER DEFAULT 0,
                pieces_completed INTEGER DEFAULT 0,
                double_six_rolls INTEGER DEFAULT 0,
                pieces_knocked INTEGER DEFAULT 0,
                total_moves INTEGER DEFAULT 0,
                total_dice_rolls INTEGER DEFAULT 0,
                FOREIGN KEY (player_id) REFERENCES players(player_id) ON DELETE CASCADE
              );
          """;

      log.debug("Created Ludo Stat table.");

      // Execute table creation
      try {
        stmt.execute(createPlayersTable);
        stmt.execute(createSnakesAndLaddersStatsTable);
        stmt.execute(createLudoStatsTable);
      } catch (SQLException e) {
        log.error("Failed to create tables: {}", e.getMessage());
        throw new RuntimeException("Table creation failed", e);
      }
      log.debug("Successfully created all tables.");

      // Create indexes for faster lookups
      stmt.execute("CREATE INDEX IF NOT EXISTS idx_snl_player_id "
          + "ON snakes_and_ladders_stats(player_id)");
      stmt.execute("CREATE INDEX IF NOT EXISTS idx_ludo_player_id ON ludo_stats(player_id)");

      log.debug("Indexes creates");

      log.debug("Database initialized successfully.");
    } catch (SQLException e) {
      System.err.println("Database initialization failed: " + e.getMessage());
    }
  }

  /**
   * Closes the given database connection.
   *
   * @param connection the Connection object to close
   */
  public static void closeConnection(Connection connection) {
    if (connection != null) {
      try {
        connection.close();
        log.debug("Database connection closed successfully.");
      } catch (SQLException e) {
        log.error("Failed to close database connection: {}", e.getMessage());
      }
    }
  }
}
