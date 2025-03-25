package no.ntnu.idatx2003.exam2025.laddersgamextreme;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import no.ntnu.idatx2003.exam2025.laddersgamextreme.dao.stats.SnakesStatsDao;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.stats.SnakesStats;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.service.DatabaseManager;

/**
 * Simple JavaFX application to test the stats tracking system.
 */
public class StatsTestingApplication extends Application {

  private ComboBox<PlayerInfo> playerSelector;
  private TextArea statsDisplay;
  private SnakesStatsDao statsDao;

  @Override
  public void start(Stage primaryStage) {
    // Initialize database
    DatabaseManager.initializeDatabase();
    statsDao = new SnakesStatsDao();

    // Populate sample players if they don't exist
    populateSamplePlayers();

    // Create the UI
    VBox root = new VBox(10);
    root.setPadding(new Insets(20));

    // Player selection
    HBox playerSelection = new HBox(10);
    Label selectLabel = new Label("Select Player:");
    playerSelector = new ComboBox<>();
    playerSelection.getChildren().addAll(selectLabel, playerSelector);

    // Stats display
    statsDisplay = new TextArea();
    statsDisplay.setPrefHeight(150);
    statsDisplay.setEditable(false);

    // Action buttons
    GridPane actionButtons = new GridPane();
    actionButtons.setHgap(10);
    actionButtons.setVgap(10);

    Button addWinBtn = new Button("Add Win");
    addWinBtn.setOnAction(e -> {
      PlayerInfo selected = playerSelector.getValue();
      if (selected != null) {
        SnakesStats stats = statsDao.loadStats(selected.id);
        stats.addWin();
        statsDao.saveStats(selected.id, stats);
        refreshStatsDisplay();
      }
    });

    Button addLossBtn = new Button("Add Loss");
    addLossBtn.setOnAction(e -> {
      PlayerInfo selected = playerSelector.getValue();
      if (selected != null) {
        SnakesStats stats = statsDao.loadStats(selected.id);
        stats.addLoss();
        statsDao.saveStats(selected.id, stats);
        refreshStatsDisplay();
      }
    });

    Button addLadderBtn = new Button("Used Ladder");
    addLadderBtn.setOnAction(e -> {
      PlayerInfo selected = playerSelector.getValue();
      if (selected != null) {
        SnakesStats stats = statsDao.loadStats(selected.id);
        stats.incrementLaddersUsed();
        statsDao.saveStats(selected.id, stats);
        refreshStatsDisplay();
      }
    });

    Button addSnakeBtn = new Button("Used Snake");
    addSnakeBtn.setOnAction(e -> {
      PlayerInfo selected = playerSelector.getValue();
      if (selected != null) {
        SnakesStats stats = statsDao.loadStats(selected.id);
        stats.incrementSnakesUsed();
        statsDao.saveStats(selected.id, stats);
        refreshStatsDisplay();
      }
    });

    Button viewStatsBtn = new Button("View Stats");
    viewStatsBtn.setOnAction(e -> refreshStatsDisplay());

    actionButtons.add(addWinBtn, 0, 0);
    actionButtons.add(addLossBtn, 1, 0);
    actionButtons.add(addLadderBtn, 0, 1);
    actionButtons.add(addSnakeBtn, 1, 1);
    actionButtons.add(viewStatsBtn, 0, 2, 2, 1);

    // Add all components to the root
    root.getChildren().addAll(
        new Label("Snakes and Ladders Stats Tester"),
        playerSelection,
        statsDisplay,
        actionButtons);

    // Create scene and show stage
    Scene scene = new Scene(root, 400, 400);
    primaryStage.setTitle("Game Stats Tester");
    primaryStage.setScene(scene);
    primaryStage.show();

    // Set player selector action - do this AFTER all UI components are initialized
    playerSelector.setOnAction(e -> refreshStatsDisplay());

    // Now it's safe to populate the player list
    refreshPlayerList();
  }

  private void refreshPlayerList() {
    List<PlayerInfo> players = getPlayers();
    playerSelector.setItems(FXCollections.observableArrayList(players));
    if (!players.isEmpty()) {
      playerSelector.setValue(players.get(0));
      refreshStatsDisplay();
    }
  }

  private void refreshStatsDisplay() {
    PlayerInfo selected = playerSelector.getValue();
    if (selected == null) {
      statsDisplay.setText("No player selected");
      return;
    }

    SnakesStats stats = statsDao.loadStats(selected.id);

    StringBuilder sb = new StringBuilder();
    sb.append("Player: ").append(selected.name).append(" (ID: ").append(selected.id).append(")\n\n");
    sb.append("Wins: ").append(stats.getWins()).append("\n");
    sb.append("Losses: ").append(stats.getLosses()).append("\n");
    sb.append("Games Played: ").append(stats.getGamesPlayed()).append("\n");
    sb.append("Win Rate: ").append(String.format("%.1f%%", stats.getWinPercentage())).append("\n\n");
    sb.append("Ladders Used: ").append(stats.getLaddersUsed()).append("\n");
    sb.append("Snakes Used: ").append(stats.getSnakesUsed()).append("\n");

    statsDisplay.setText(sb.toString());
  }

  private List<PlayerInfo> getPlayers() {
    List<PlayerInfo> players = new ArrayList<>();

    try (Connection conn = DatabaseManager.connect();
        PreparedStatement stmt = conn.prepareStatement("SELECT player_id, player_name FROM players");
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        int id = rs.getInt("player_id");
        String name = rs.getString("player_name");
        players.add(new PlayerInfo(id, name));
      }

    } catch (SQLException e) {
      System.err.println("Error loading players: " + e.getMessage());
    }

    return players;
  }

  private void populateSamplePlayers() {
    // Check if we already have players
    try (Connection conn = DatabaseManager.connect();
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM players");
        ResultSet rs = stmt.executeQuery()) {

      if (rs.next() && rs.getInt(1) > 0) {
        // Players already exist, no need to create sample ones
        return;
      }

    } catch (SQLException e) {
      System.err.println("Error checking existing players: " + e.getMessage());
    }

    // No players exist, create sample ones
    String[] sampleNames = { "Alice", "Bob", "Charlie" };
    int[] sampleAges = { 25, 30, 22 };

    try (Connection conn = DatabaseManager.connect();
        PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO players (player_name, player_age) VALUES (?, ?)",
            PreparedStatement.RETURN_GENERATED_KEYS)) {

      for (int i = 0; i < sampleNames.length; i++) {
        stmt.setString(1, sampleNames[i]);
        stmt.setInt(2, sampleAges[i]);
        stmt.executeUpdate();

        // Get the generated player ID
        try (ResultSet keys = stmt.getGeneratedKeys()) {
          if (keys.next()) {
            int playerId = keys.getInt(1);
            // Create empty stats for this player
            SnakesStats stats = new SnakesStats();
            statsDao.saveStats(playerId, stats);
          }
        }
      }

      System.out.println("Sample players created successfully");

    } catch (SQLException e) {
      System.err.println("Error creating sample players: " + e.getMessage());
    }
  }

  /**
   * Helper class to store player information for the combo box.
   */
  private static class PlayerInfo {
    private final int id;
    private final String name;

    public PlayerInfo(int id, String name) {
      this.id = id;
      this.name = name;
    }

    @Override
    public String toString() {
      return name;
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}