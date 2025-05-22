package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import no.ntnu.idatx2003.exam2025.boardgames.controller.BoardGameController;

/**
 * View for making a simple navbar to enter and exit board games.
 */
public class NavBarView {
  private final StackPane root;
  private final HBox layout;
  private final Rectangle background;
  private final Button settingsButton;
  private final Button infoButton;
  private final Button mainMenuButton;
  private final BoardGameController controller;

  /**
   * Default constructor.
   *
   * @param controller object for facilitating user interaction.
   */
  public NavBarView(BoardGameController controller) {
    root = new StackPane();
    layout = new HBox(5);
    background = new Rectangle();
    settingsButton = new Button("Settings");
    infoButton = new Button("Info");
    mainMenuButton = new Button("Main Menu");
    this.controller = controller;
    layout.getChildren().addAll(settingsButton, infoButton, mainMenuButton);
    root.getChildren().addAll(background, layout);
    configureViews();
  }

  private void configureViews() {
    settingsButton.setOnAction(e -> {
      controller.showMissingOptionAlert();
    });

    infoButton.setOnAction(e -> {
      controller.showMissingOptionAlert();
    });

    mainMenuButton.setOnAction(e -> {
      controller.exitToMainMenu();
    });

    root.setMaxSize(300, 100);
  }

  public Parent getRoot() {
    return root;
  }

  private void assignStyling() {
    background.getStyleClass().add("board-game-menu-background");
    mainMenuButton.getStyleClass().add("button-accent");
  }
}
