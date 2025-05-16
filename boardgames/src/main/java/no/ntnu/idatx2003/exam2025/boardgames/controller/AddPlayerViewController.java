package no.ntnu.idatx2003.exam2025.boardgames.controller;

import javafx.scene.Parent;
import no.ntnu.idatx2003.exam2025.boardgames.model.GameSession;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneManager;
import no.ntnu.idatx2003.exam2025.boardgames.util.view.AlertUtil;
import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDao;
import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDaoImpl;
import java.sql.SQLException;

/**
 * A Controller used for working between a player view and a Game Session.
 */
public class AddPlayerViewController {
  private final GameSession gameSession;
  private final SceneManager sceneManager;
  private final PlayerDaoImpl playerDao;

  /**
   * A Controller used for working between a player view and a Game Session.
   *
   * @param gameSession the current Game Session.
   */
  public AddPlayerViewController(GameSession gameSession, SceneManager sceneManager, PlayerDaoImpl playerDao) {
    this.gameSession = gameSession;
    this.sceneManager = sceneManager;
    this.playerDao = playerDao;

  }

  /**
   * Creates a new player with the given name and age, adds it to the database,
   * and returns an error message if any.
   *
   * @param name    the name of the player
   * @param ageText the age of the player as a String
   * @return null if successful, otherwise an error message
   */
  public String createAndAddPlayer(String name, String ageText) {

    if (name == null || name.trim().isEmpty()) {
      AlertUtil.showError("Invalid input", "Name cannot be empty");
    }

    int age;

    try {
      // convert to int
      age = Integer.parseInt(ageText);
      if (age < 0 || age > 120) {
        return "Age must be between 0 and 120";
      }
    } catch (NumberFormatException e) {
      return "Age needs to be a valid number";
    }

    // persist player

    try {
      Player player = new Player(0, name, age);
      int playerId = playerDao.create(player);
      player.setPlayerId(playerId);
    } catch (SQLException e) {
      return "Could not create player: " + e.getMessage();
    }
    return null;
  }

  public void closeWindow(Parent overlay) {
    sceneManager.closeOverlay(overlay);
  }
}
