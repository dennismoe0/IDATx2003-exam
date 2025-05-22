package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import no.ntnu.idatx2003.exam2025.boardgames.controller.AddPlayerViewController;
import no.ntnu.idatx2003.exam2025.boardgames.exception.InvalidUserDataException;
import no.ntnu.idatx2003.exam2025.boardgames.util.view.AlertUtil;

/**
 * View for adding new players to the board game application.
 * Provides input fields for player name and age, and buttons to add or close.
 */
public class AddPlayerView {

  private final StackPane root;
  private final Rectangle backdrop;
  private Label title;
  private static final float WIDTH = 780;
  private static final float HEIGHT = 540;
  private final VBox layout;
  private TitledFieldView nameInput;
  private TitledFieldView ageInput;
  private final Button addButton;
  private final Button closeButton;
  private final AddPlayerViewController controller;

  /**
   * Primary view for Adding new Players to the game.
   *
   * @param controller the controller that controls the view.
   */
  public AddPlayerView(AddPlayerViewController controller) {
    this.controller = controller;
    root = new StackPane();
    backdrop = new Rectangle(WIDTH, HEIGHT);
    layout = new VBox(7);
    addButton = new Button("Add Player");
    closeButton = new Button("Close Window");
    configureFields();
    assignStyling();
    configureButton();
    root.getChildren().addAll(backdrop, layout);
  }

  private void configureFields() {
    title = new Label("Add Player");
    nameInput = new TitledFieldView("Name", "Add the player name...", 200, 50);
    ageInput = new TitledFieldView("Age", "Add the player age...", 200, 50);
    layout.getChildren().addAll(
        title, nameInput.getRoot(), ageInput.getRoot(), addButton, closeButton);
    root.setMaxSize(WIDTH, HEIGHT);
    layout.setAlignment(Pos.CENTER);
  }

  private void configureButton() {
    addButton.setOnAction(event -> {
      try {
        String name = nameInput.getFieldText();
        String ageText = ageInput.getFieldText();

        String error = controller.createAndAddPlayer(name, ageText);

        if (error == null) {
          AlertUtil.showInfo("Player created", "Player " + name + " created successfully.");
          nameInput.clearField();
          ageInput.clearField();

        } else {
          AlertUtil.showError("Invalid input", error);
        }

      } catch (InvalidUserDataException e) {
        AlertUtil.showError("Invalid input", e.getMessage());
      }
    });

    closeButton.setOnAction(event -> {
      controller.closeWindow(this.getRoot());
    });
  }

  private void assignStyling() {
    title.getStyleClass().add("menu-title");
    backdrop.getStyleClass().add("menu-background");
  }

  public Parent getRoot() {
    return root;
  }
}
