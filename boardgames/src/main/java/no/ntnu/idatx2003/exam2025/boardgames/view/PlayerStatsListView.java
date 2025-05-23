package no.ntnu.idatx2003.exam2025.boardgames.view;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import no.ntnu.idatx2003.exam2025.boardgames.controller.PlayerListViewController;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneManager;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneRegister;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.ChangeScreenCommand;
import no.ntnu.idatx2003.exam2025.boardgames.util.view.AlertUtil;
import org.slf4j.Logger;

/**
 * A view that lists all players with a "Show Statistics" button for each.
 * Users select a game from a dropdown and click to view stats using the
 * existing PlayerStatisticsView overlay.
 * This should have its own controller but we dont have more time
 */
public class PlayerStatsListView {
  private final VBox layout;
  private final VBox playerListVbox;
  private final ScrollPane scrollPane;
  private final PlayerListViewController controller;
  private final ComboBox<String> gameComboBox;
  private static final Logger log = org.slf4j.LoggerFactory.getLogger(PlayerStatsListView.class);
  private final SceneRegister sceneRegister;
  private final SceneManager sceneManager;

  /**
   * Constructs a PlayerStatsListView with the given controller, scene register,
   * and scene manager.
   *
   * @param controller    the controller for player list operations
   * @param sceneRegister the scene register for managing scenes
   * @param sceneManager  the scene manager for switching scenes
   */
  public PlayerStatsListView(PlayerListViewController controller, SceneRegister sceneRegister,
      SceneManager sceneManager) {
    this.controller = controller;
    this.sceneRegister = sceneRegister;
    this.sceneManager = sceneManager;
    this.layout = new VBox(10);
    this.playerListVbox = new VBox(8);
    this.scrollPane = new ScrollPane(playerListVbox);
    this.gameComboBox = new ComboBox<>();
    layout.setAlignment(Pos.CENTER);
    layout.setStyle(
        "-fx-background-color: white; "
            + "-fx-background-radius: 15; "
            + "-fx-padding: 20; "
            + "-fx-border-color: #CCCCCC; "
            + "-fx-border-radius: 15;");
    layout.getStyleClass().add("main-menu-background");
    layout.sceneProperty().addListener((obs, oldScene, newScene) -> {
      if (newScene != null) {
        layout.prefWidthProperty().bind(newScene.widthProperty().multiply(0.5));
      }
    });
    Button returnButton = new Button("Return to Main Menu");
    returnButton.setOnAction(e -> {
      new ChangeScreenCommand(sceneRegister, sceneManager, "main-menu").execute();
    });
    HBox topBar = new HBox();
    topBar.getChildren().add(returnButton);
    topBar.setAlignment(Pos.TOP_RIGHT);
    configureGameSelector();
    configureScrollPane();

    layout.getChildren().addAll(returnButton, new Label("Select Game:"), gameComboBox, scrollPane);

    loadPlayers();
  }

  private void configureGameSelector() {
    try {
      List<String> games = controller.getSupportedGames();
      gameComboBox.getItems().setAll(games);
      if (!games.isEmpty()) {
        gameComboBox.setValue(games.get(0));
      }
    } catch (Exception e) {
      AlertUtil.showError("Failed to load games", e.getMessage());
    }
  }

  private void configureScrollPane() {
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefViewportHeight(400);
  }

  private void loadPlayers() {
    playerListVbox.getChildren().clear();

    try {
      List<Player> players = controller.getAllPlayers();
      if (players.isEmpty()) {
        playerListVbox.getChildren().add(new Label("No players found."));
        return;
      }

      for (Player player : players) {
        HBox row = new HBox(20);
        Label nameLabel = new Label(player.getPlayerName());
        nameLabel.setMinWidth(150);

        Button showStatsButton = new Button("Show Statistics");
        showStatsButton.setMinWidth(140);
        showStatsButton.setOnAction(e -> onShowStats(player));

        row.getChildren().addAll(nameLabel, showStatsButton);
        playerListVbox.getChildren().add(row);
      }
    } catch (SQLException e) {
      AlertUtil.showError("Failed to load players", e.getMessage());
    }
  }

  private void onShowStats(Player player) {
    String selectedGame = gameComboBox.getValue();
    if (selectedGame == null || selectedGame.isEmpty()) {
      AlertUtil.showError("No game selected", "Please select a game first.");
      return;
    }
    try {
      LinkedHashMap<String, Integer> stats = controller.getPlayerStatsMap(player, selectedGame);
      // Use existing PlayerStatisticsView overlay
      PlayerStatisticsView statsView = new PlayerStatisticsView(player, stats, selectedGame);
      Window owner = layout.getScene().getWindow();
      statsView.showOverlay(owner);
    } catch (SQLException ex) {
      log.error("Error loading stats for {} in {}", player.getPlayerName(), selectedGame, ex);
      AlertUtil.showError("Failed to load statistics", ex.getMessage());
    } catch (IllegalArgumentException iae) {
      AlertUtil.showError("Unsupported game", iae.getMessage());
    }
  }

  /**
   * Returns the root node to embed in a scene or parent layout.
   */
  public Parent getRoot() {
    return layout;
  }
}
