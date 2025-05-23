package no.ntnu.idatx2003.exam2025.boardgames.service;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

/**
 * Manager used to change scenes/navigate the program.
 */
public class SceneManager {
  private static final Logger logger = Log.get(SceneManager.class);
  private Scene scene;
  private final Stage primaryStage;
  private Parent activeRoot;
  private StackPane rootPane;
  private boolean initialized;

  /**
   * Default Constructor for Scene Manager.
   *
   * @param primaryStage the JavaFX stage for the program.
   */
  public SceneManager(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  /**
   * Method for setting the active scene.
   *
   * @param scene a JavaFX scene object.
   */
  public void setScene(Scene scene) {
    logger.info("Setting scene to " + scene);
    this.scene = scene;
  }

  /**
   * Initialization method for use when first starting the program.
   *
   * @param root a JavaFX node representing the root object of a scene.
   */
  public void initialize(Parent root) {
    if (!initialized) {
      logger.info("Initializing scene");
      rootPane = new StackPane();
      rootPane.getStyleClass().add("primary-window-background");
      rootPane.getChildren().add(root);
      rootPane.setAlignment(Pos.CENTER);

      scene = new Scene(rootPane, 1400, 750);
      try {
        scene.getStylesheets().add(
            getClass().getResource("/assets/style/styles.css").toExternalForm());
      } catch (NullPointerException e) {
        logger.error(e.getMessage());
      }

      primaryStage.setScene(scene);
    }
  }

  public Scene getScene() {
    return scene;
  }

  /**
   * A method that can be called to change scenes in the program.
   *
   * @param root the root node for the scene to be swapped to.
   */
  public void setView(Parent root) {
    logger.info("Changing view to " + root);
    if (scene == null) {
      logger.info("No Scene object set, getting from Primary Stage.");
      initialize(root);
    }
    if (!rootPane.getChildren().isEmpty()) {
      rootPane.getChildren().clear();
    }
    rootPane.getChildren().add(root);
    activeRoot = root;
  }

  /**
   * Method for displaying an overlay over top of the active scene.
   *
   * @param overlay the scene to overlay on top of the active scene.
   */
  public void showOverlay(Parent overlay) {
    logger.info("Showing overlay");
    rootPane.getChildren().add(overlay);
  }

  /**
   * A method for closing a currently active overlay.
   *
   * @param overlay the overlay object to close.
   */
  public void closeOverlay(Parent overlay) {
    logger.info("Closing overlay");
    rootPane.getChildren().remove(overlay);
  }

  /**
   * Method for getting the current root of the scene.
   *
   * @return Returns a node object as a Parent.
   */
  public Parent getRoot() {
    if (activeRoot == null) {
      throw new IllegalStateException("Root not set");
    }
    return activeRoot;
  }
}
