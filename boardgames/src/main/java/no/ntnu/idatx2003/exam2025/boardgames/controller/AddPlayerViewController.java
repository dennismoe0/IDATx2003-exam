package no.ntnu.idatx2003.exam2025.boardgames.controller;

import javafx.scene.Parent;
import no.ntnu.idatx2003.exam2025.boardgames.model.GameSession;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneManager;

/**
 * A Controller used for working between a player view and a Game Session.
 */
public class AddPlayerViewController {
  private final GameSession gameSession;
  private final SceneManager sceneManager;

  /**
   * A Controller used for working between a player view and a Game Session.
   *
   * @param gameSession the current Game Session.
   */
  public AddPlayerViewController(GameSession gameSession, SceneManager sceneManager) {
    this.gameSession = gameSession;
    this.sceneManager = sceneManager;
  }

  /**
   * Constructs a player object from input fields and adds it to the game session.
   *
   * @param name a string representing the players name.
   * @param age an int representing the players age.
   */
  public void AddPlayer(String name, int age) {
    Player player = new Player(0, name, age);
    gameSession.addPlayer(player);
  }

  public void closeWindow(Parent overlay) {
    sceneManager.closeOverlay(overlay);
  }
}
