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
  private Stage primaryStage;
  private Parent activeRoot;
  private boolean initialized;

  public SceneManager(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  public void setScene(Scene scene) {
    logger.info("Setting scene to " + scene);
    this.scene = scene;
  }

  public void initialize(Parent root) {
    if (!initialized) {
      StackPane stackpane = new StackPane();
      stackpane.getStyleClass().add("primary-window-background");
      stackpane.getChildren().add(root);
      stackpane.setAlignment(Pos.CENTER);

      scene = new Scene(stackpane, 1400, 750);
      scene.getStylesheets().add(
          getClass().getResource("/assets/style/styles.css").toExternalForm());
      primaryStage.setScene(scene);
    } else {
      return;
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
  public void changeRoot(Parent root) {
    logger.info("Changing root to " + root);
    if (scene == null) {
      logger.info("No Scene object set, getting from Primary Stage.");
      scene = primaryStage.getScene();
    }
    scene.setRoot(root);
    activeRoot = root;
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
