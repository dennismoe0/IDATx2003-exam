package no.ntnu.idatx2003.exam2025.boardgames.view;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatx2003.exam2025.boardgames.controller.PlayerListViewController;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.util.view.AlertUtil;

/**
 * Player list view handles displaying available Player Objects from the database.
 */
public class PlayerListView {
  private final VBox playerListLayout;
  private final ScrollPane scrollPane;
  private final PlayerListViewController controller;
  private final ComboBox<String> gameComboBox;
  private final Button refreshButton;

  // Have some boxes next to player names that can be
  // checked to "add to game" / pool of active players
  // Should be retrieved from database and added as Player Objects
  // if unchecked = delete that Player object instance

  /**
   * Default constructor for the list view.
   *
   * @param controller a PlayerlistViewController built to work with player lists.
   */
  public PlayerListView(PlayerListViewController controller) {
    this.controller = controller;
    refreshButton = new Button("Refresh List");
    playerListLayout = new VBox(5);
    scrollPane = new ScrollPane(playerListLayout);
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefViewportHeight(400);

    gameComboBox = new ComboBox<>();
    gameComboBox.getItems().addAll(controller.getSupportedGames());
    if (!gameComboBox.getItems().isEmpty()) {
      gameComboBox.setValue(gameComboBox.getItems().getFirst());
    }
    configureButton();

    VBox container = new VBox(10, gameComboBox, playerListLayout, refreshButton);
    scrollPane.setContent(container);

    loadPlayers();
  }

  private void configureButton() {
    refreshButton.setOnAction(event -> loadPlayers());
  }

  private void loadPlayers() {
    playerListLayout.getChildren().clear();

    Label nameHeader = new Label("Name");
    nameHeader.setMinWidth(120);
    Label ageHeader = new Label("Age");
    ageHeader.setMinWidth(60);
    Label idHeader = new Label("ID");
    idHeader.setMinWidth(60);
    Label actionHeader = new Label("Delete");
    actionHeader.setMinWidth(80);
    Label selectHeader = new Label("Select");
    selectHeader.setMinWidth(60);

    HBox header = new HBox(20);
    header.getChildren().addAll(nameHeader, ageHeader, idHeader, actionHeader, selectHeader);
    playerListLayout.getChildren().add(header);

    try {
      List<Player> players = controller.getAllPlayers();

      for (Player player : players) {

        Label nameLabel = new Label(player.getPlayerName());
        nameLabel.setMinWidth(120);

        Label ageLabel = new Label(String.valueOf(player.getPlayerAge()));
        ageLabel.setMinWidth(60);

        Label idLabel = new Label(String.valueOf(player.getPlayerId()));
        idLabel.setMinWidth(60);

        Button deleteButton = new Button("Delete");
        deleteButton.setMinWidth(80);
        deleteButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> {
          try {
            controller.deletePlayer(player.getPlayerId());
            loadPlayers();
          } catch (SQLException ex) {
            AlertUtil.showError("Failed to delete player", ex.getMessage());
          }
        });

        CheckBox selectBox = new CheckBox();
        selectBox.setMinWidth(60);
        selectBox.setOnAction(e -> {
          if (selectBox.isSelected()) {
            controller.addPlayerToGameSession(player);
          } else if (!selectBox.isSelected()) {
            controller.removePlayerFromGameSession(player);
          }
        });

        Button showStatsButton = new Button("Show statistics");
        showStatsButton.setMinWidth(120);
        showStatsButton.setOnAction(e -> {
          try {
            String selectedGame = gameComboBox.getValue();
            LinkedHashMap<String, Integer> stats = controller.getPlayerStatsMap(
                player, selectedGame);
            PlayerStatisticsView statsView = new PlayerStatisticsView(player, stats, selectedGame);
            statsView.showOverlay(playerListLayout.getScene().getWindow());
          } catch (SQLException ex) {
            AlertUtil.showError("Failed to load statistics", ex.getMessage());
          }
        });

        HBox row = new HBox(20);
        row.getChildren().addAll(
            nameLabel, ageLabel, idLabel, deleteButton, selectBox, showStatsButton);
        playerListLayout.getChildren().add(row);
      }
      if (players.isEmpty()) {
        playerListLayout.getChildren().add(new Label("No players found in the database."));
      }
    } catch (SQLException e) {
      AlertUtil.showError("Failed to load players: ", e.getMessage());
    }
  }

  public Parent getRoot() {
    return scrollPane;
  }
}
