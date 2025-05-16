package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;

public class PlayerItemView {
  private Player player;
  private Label name;
  private Label age;
  private ImageView image;
  private HBox root;
  private VBox infoOrganizer;
  private static final float width = 300f;
  private static final float height = 100f;

  public PlayerItemView(Player player) {
    this.player = player;
    name = new Label(player.getPlayerName());
    age = new Label(String.valueOf(player.getPlayerAge()));
    image = new ImageView();
    root = new HBox(5);
    infoOrganizer = new VBox(3);
  }

  private void configureView() {
    root.setMaxSize(width, height);
    image.setFitWidth(75f);
    image.setFitHeight(75f);
    infoOrganizer.getChildren().addAll(name, age);
    root.getChildren().addAll(infoOrganizer, image);
  }
}
