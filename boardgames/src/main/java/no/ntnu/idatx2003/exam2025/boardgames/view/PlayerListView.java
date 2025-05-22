package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.slf4j.Logger;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import no.ntnu.idatx2003.exam2025.boardgames.controller.PlayerListViewController;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import no.ntnu.idatx2003.exam2025.boardgames.util.view.AlertUtil;

public class PlayerListView {
  private final VBox layout;
  private final VBox playerListVBox;
  private final ScrollPane scrollPane;
  private final PlayerListViewController controller;
  private ComboBox<String> gameComboBox;
  private Button refreshButton;
  private static final Logger log = Log.get(PlayerListView.class);

  // Have some boxes next to player names that can be
  // checked to "add to game" / pool of active players
  // Should be retrieved from database and added as Player Objects
  // if unchecked = delete that Player object instance
  public PlayerListView(PlayerListViewController controller) {
    this.controller = controller;
    refreshButton = new Button("Refresh List");
    playerListVBox = new VBox(5);
    layout = new VBox(3);
    scrollPane = new ScrollPane(playerListVBox);
    scrollPane.setFitToWidth(true);
    scrollPane.setVvalue(0);
    scrollPane.setHvalue(0);
    scrollPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
      if (newScene != null) {
        scrollPane.prefViewportWidthProperty().bind(
            newScene.widthProperty().multiply(0.7));
        scrollPane.prefViewportHeightProperty().bind(
            newScene.heightProperty().multiply(0.5));
        scrollPane.viewportBoundsProperty().addListener((o, oldBounds, newBounds) -> {
          scrollPane.setVvalue(0);
          scrollPane.setHvalue(0);
        });
      }
    });

    configureButton();

    VBox container = new VBox(10, playerListVBox);
    scrollPane.setContent(container);
    scrollPane.getStyleClass().add("move-history");
    scrollPane.viewportBoundsProperty().addListener((obs, oldVal, newVal) -> {
      Region viewport = (Region) scrollPane.lookup(".viewport");
      if (viewport != null) {
        viewport.setStyle("-fx-background-color: transparent;");
      }
    });
    layout.getChildren().add(scrollPane);
    layout.getChildren().add(refreshButton);

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
    Label actionHeader = new Label("Delete");
    actionHeader.setMinWidth(80);
    Label selectHeader = new Label("Player selection");
    selectHeader.setMinWidth(60);
    Label pieceHeader = new Label("Select piece use");
    pieceHeader.setMinWidth(60);

    header.getChildren().addAll(nameHeader, ageHeader, actionHeader, selectHeader, pieceHeader);
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
            log.debug("Attempting to delete player with ID {} and name {}", player.getPlayerId(),
                player.getPlayerName());
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
            // Check if 5 players are already selected
            boolean allowed = controller.selectPlayer(player);
            if (!allowed) {
              AlertUtil.showError(
                  "Too many selections",
                  "No more than 5 players can be selected at the same time.");
              selectBox.setSelected(false);
            }
          } else {
            controller.removePlayerFromGameSession(player);
          }
        });

        Button pickPieceButton = new Button("Pick Token");
        pickPieceButton.setMinWidth(100);
        pickPieceButton.setOnAction(e -> {

          if (!selectBox.isSelected()) {
            AlertUtil.showError("Player not selected", "Please checkmark the player first.");
            return;
          }

          log.debug("Attempting to open playing piece window");
          String assetPath = controller.openPiecePicker(player);

          if (controller.isDuplicatePieceSelection(player, assetPath)) {
            AlertUtil.showError(
                "Duplicate Piece",
                "Please choose a piece anyone hasn't already selected.");
            return;
          }

          player.setPlayerPieceAssetPath(assetPath);
          controller.updatePlayingPiece(player, assetPath);
          InputStream assetStream = getClass().getResourceAsStream(assetPath);
          if (assetStream == null) {
            assetStream = getClass().getResourceAsStream("/assets/pieces/default_piece.png");
          }
          ImageView pieceImageView = new ImageView(new Image(assetStream));
          pieceImageView.setFitWidth(32);
          pieceImageView.setFitHeight(32);
          pieceImageView.setPreserveRatio(true);
          pickPieceButton.setGraphic(pieceImageView);
          pickPieceButton.setText("<- Piece chosen");
        });

        // The order here matches the header!
        row.getChildren().addAll(nameLabel, ageLabel, deleteButton, selectBox, pickPieceButton);
        playerListVBox.getChildren().add(row);
      }
      if (players.isEmpty()) {
        playerListVBox.getChildren().add(new Label("No players found in the database."));
      }
    } catch (SQLException e) {
      AlertUtil.showError("Failed to load players: ", e.getMessage());
    }
  }

  public PlayerListViewController getController() {
    return controller;
  }

  public Parent getRoot() {
    return layout;
  }
}
