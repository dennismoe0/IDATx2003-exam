package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import no.ntnu.idatx2003.exam2025.boardgames.controller.PlayerListViewController;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.util.view.AlertUtil;

public class PlayerListView {
  private final VBox playerListVBox;
  private final ScrollPane scrollPane;
  private final PlayerListViewController controller;

  // Have some boxes next to player names that can be
  // checked to "add to game" / pool of active players
  // Should be retrieved from database and added as Player Objects
  // if unchecked = delete that Player object instance
  public PlayerListView(PlayerListViewController controller) {
    this.controller = controller;
    playerListVBox = new VBox(5);
    scrollPane = new ScrollPane(playerListVBox);
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefViewportHeight(400);

    loadPlayers();
  }

  private void loadPlayers() {
    playerListVBox.getChildren().clear();

    try {
      // Gets from database
      List<Player> players = controller.getAllPlayers();

      for (Player player : players) {
        HBox row = new HBox(20);
        row.getChildren().addAll(
            new Label("Name: " + player.getPlayerName()),
            new Label("Age: " + player.getPlayerAge()),
            new Label("ID: " + player.getPlayerId()));
        playerListVBox.getChildren().add(row);
      }
    } catch (SQLException e) {
      AlertUtil.showError("Failed to load players: ", e.getMessage());
    }
  }

  public Parent getRoot() {
    return scrollPane;
  }
}
