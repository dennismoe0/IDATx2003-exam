package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import no.ntnu.idatx2003.exam2025.boardgames.controller.AddPlayerViewController;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.util.view.AlertUtil;
import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDao;
import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDaoImpl;
import java.sql.SQLException;

public class AddPlayerView {
  private StackPane root;
  private Rectangle backdrop;
  private Label title;
  private static final float width = 780;
  private static final float height = 540;
  private VBox layout;
  private TitledFieldView nameInput;
  private TitledFieldView ageInput;
  private Button addButton;
  private AddPlayerViewController controller;

  public AddPlayerView(AddPlayerViewController controller) {
    this.controller = controller;
    root = new StackPane();
    backdrop = new Rectangle(width, height);
    layout = new VBox(7);
    addButton = new Button("Add Player");
    configureFields();
    assignStyling();
    configureButton();
    root.getChildren().addAll(backdrop, layout);
  }

  private void configureFields() {
    title = new Label("Add Player");
    nameInput = new TitledFieldView("Name", "Add the player name...", 200, 50);
    ageInput = new TitledFieldView("Age", "Add the player age...", 200, 50);
    layout.getChildren().addAll(title, nameInput.getRoot(), ageInput.getRoot(), addButton);
    root.setMaxSize(width, height);
    layout.setAlignment(Pos.CENTER);
  }

  private void configureButton() {
    addButton.setOnAction(event -> {

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
