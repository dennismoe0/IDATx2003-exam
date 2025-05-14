package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class AddPlayerView {
  private StackPane root;
  private Rectangle backdrop;
  private Label title;
  private static final float width = 780;
  private static final float height = 540;
  private VBox layout;
  private TitledFieldView nameInput;
  private TitledFieldView ageInput;

  public AddPlayerView() {
    root = new StackPane();
    backdrop = new Rectangle(width, height);
    layout = new VBox(7);
    configureFields();
    assignStyling();
    root.getChildren().addAll(backdrop, layout);
  }

  private void configureFields() {
    title = new Label("Add Player");
    nameInput = new TitledFieldView("Name", "Add the player name...", 200, 50);
    ageInput = new TitledFieldView("Age", "Add the player age...", 200, 50);
    layout.getChildren().addAll(title, nameInput.getRoot(), ageInput.getRoot());
    root.setMaxSize(width, height);
    layout.setAlignment(Pos.CENTER);
  }

  private void assignStyling() {
    title.getStyleClass().add("menu-title");
    backdrop.getStyleClass().add("menu-background");
  }

  public Parent getRoot() {
    return root;
  }
}
