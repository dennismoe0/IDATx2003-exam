package no.ntnu.idatx2003.exam2025.boardgames.service;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Manager used to change scenes/navigate the program.
 */
public class SceneManager {
  private Scene scene;
  private Stage primaryStage;
  private Parent activeRoot;

  public SceneManager(Stage primaryStage) {
    this.primaryStage = primaryStage;
  }

  public void setScene(Scene scene) {
    this.scene = scene;
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
    scene.setRoot(root);
    activeRoot = root;
  }

  public Parent getRoot(){
    if(activeRoot == null){
      throw new IllegalStateException("Root not set");
    }
    return activeRoot;
  }
}
