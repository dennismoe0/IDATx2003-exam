package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import no.ntnu.idatx2003.exam2025.boardgames.controller.PlayerListViewController;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.util.view.AlertUtil;

public class PlayerListView {
  private final VBox playerListVBox;
  private final ScrollPane scrollPane;
  private final PlayerListViewController controller;
  private ComboBox<String> gameComboBox;
  private Button refreshButton;
  private HashMap<Player, String> playingPieceMap = new HashMap<>();

  // Have some boxes next to player names that can be
  // checked to "add to game" / pool of active players
  // Should be retrieved from database and added as Player Objects
  // if unchecked = delete that Player object instance
  public PlayerListView(PlayerListViewController controller) {
    this.controller = controller;
    refreshButton = new Button("Refresh List");
    playerListVBox = new VBox(5);
    scrollPane = new ScrollPane(playerListVBox);
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefViewportHeight(400);

    configureButton();

    VBox container = new VBox(10, playerListVBox, refreshButton);
    scrollPane.setContent(container);

    loadPlayers();
  }

  private void configureButton() {
    refreshButton.setOnAction(event -> loadPlayers());
  }

  private void loadPlayers() {
    playerListVBox.getChildren().clear();

    HBox header = new HBox(20);
    Label nameHeader = new Label("Name");
    nameHeader.setMinWidth(120);
    Label ageHeader = new Label("Age");
    ageHeader.setMinWidth(60);
    Label idHeader = new Label("ID");
    idHeader.setMinWidth(60);
    Label actionHeader = new Label("Delete");
    actionHeader.setMinWidth(80);
    Label selectHeader = new Label("Select piece to use");
    selectHeader.setMinWidth(60);

    header.getChildren().addAll(nameHeader, ageHeader, idHeader, actionHeader, selectHeader);
    playerListVBox.getChildren().add(header);

    try {
      List<Player> players = controller.getAllPlayers();

      for (Player player : players) {
        HBox row = new HBox(20);

        Label nameLabel = new Label(player.getPlayerName());
        nameLabel.setMinWidth(120);

        Label ageLabel = new Label(String.valueOf(player.getPlayerAge()));
        ageLabel.setMinWidth(60);

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

        // The order here matches the header!
        row.getChildren().addAll(nameLabel, ageLabel, deleteButton, selectBox);
        playerListVBox.getChildren().add(row);
      }
      if (players.isEmpty()) {
        playerListVBox.getChildren().add(new Label("No players found in the database."));
      }
    } catch (SQLException e) {
      AlertUtil.showError("Failed to load players: ", e.getMessage());
    }
  }

  /**
   * Updates the playing piece asset path for the specified player.
   *
   * @param player    the player whose playing piece is to be updated
   * @param assetPath the asset path of the playing piece
   */
  public void updatePlayingPiece(Player player, String assetPath) {
    playingPieceMap.put(player, assetPath);
  }

  /**
   * Retrieves the asset path for the specified player's playing piece.
   *
   * @param player the player whose playing piece asset path is to be retrieved
   * @return the asset path of the player's playing piece, or a default path if
   *         not set
   */
  public String getPlayingPieceAsset(Player player) {
    return playingPieceMap.getOrDefault(player, "/assets/pieces/default_piece.png");
  }

  public Parent getRoot() {
    return scrollPane;
  }
}
