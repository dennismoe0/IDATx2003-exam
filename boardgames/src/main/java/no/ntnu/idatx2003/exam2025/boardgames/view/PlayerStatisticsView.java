package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class PlayerStatisticsView {
  private final Player player;
  private final LinkedHashMap<String, Integer> stats;
  private final String game;

  public PlayerStatisticsView(Player player, LinkedHashMap<String, Integer> stats, String game) {
    this.player = player;
    this.stats = stats;
    this.game = game;
  }

  public void showOverlay(Window owner) {
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.initOwner(owner);
    dialog.setTitle("Statistics for " + player.getPlayerName() + " (" + game + ")");

    VBox layout = new VBox(10);
    layout.setAlignment(Pos.CENTER);
    layout.setStyle("-fx-padding: 20;");

    Label title = new Label("Statistics for " + player.getPlayerName() + " (" + game + ")");
    layout.getChildren().add(title);

    for (Map.Entry<String, Integer> entry : stats.entrySet()) {
      layout.getChildren().add(new Label(entry.getKey() + ": " + entry.getValue()));
    }

    Button closeButton = new Button("Close");
    closeButton.setOnAction(e -> dialog.close());
    layout.getChildren().add(closeButton);

    Scene scene = new Scene(layout);
    dialog.setScene(scene);
    dialog.showAndWait();
  }
}