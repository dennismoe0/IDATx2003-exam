package no.ntnu.idatx2003.exam2025.boardgames.controller;

import no.ntnu.idatx2003.exam2025.boardgames.model.GameSession;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;

/**
 * A Controller used for working between a player view and a Game Session.
 */
public class AddPlayerViewController {
  private final GameSession gameSession;

  /**
   * A Controller used for working between a player view and a Game Session.
   *
   * @param gameSession the current Game Session.
   */
  public AddPlayerViewController(GameSession gameSession) {
    this.gameSession = gameSession;
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
}
