package no.ntnu.idatx2003.exam2025.boardgames.service;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Manager used to change scenes/navigate the program.
 */
public class SceneManager {
  private Scene scene;
  private Node activeRoot;

  public SceneManager(Scene scene) {
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
  public void changeRoot(Node root) {
    scene.setRoot(root.getParent());
  }

  public void getRoot(){

  }
}
