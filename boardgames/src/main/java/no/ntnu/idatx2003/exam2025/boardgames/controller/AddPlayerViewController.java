package no.ntnu.idatx2003.exam2025.boardgames.controller;

import java.sql.SQLException;
import javafx.scene.Parent;
import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.exception.InvalidUserDataException;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneManager;

/**
 * A Controller used for working between a player view and a Game Session.
 */
public class AddPlayerViewController {
  private final SceneManager sceneManager;
  private final PlayerDaoImpl playerDao;

  /**
   * A Controller used for working between a player view and a Game Session.
   *
   * @param sceneManager the current Game Session.
   * @param playerDao the database access object
   */
  public AddPlayerViewController(
      SceneManager sceneManager, PlayerDaoImpl playerDao) {
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
      throw new InvalidUserDataException("Name Cannot be Empty");
    }

    int age;

    try {
      // convert to int
      age = Integer.parseInt(ageText);
      if (age < 0) {
        throw new InvalidUserDataException("Age Cannot be Negative");
      }
      if (age > 120) {
        throw new InvalidUserDataException("Age Cannot be Greater than 120");
      }
    } catch (NumberFormatException e) {
      throw new InvalidUserDataException("Age must be a number");
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

  /**
   * Method for closing an overaly window.
   *
   * @param overlay the window to be closed.
   */
  public void closeWindow(Parent overlay) {
    sceneManager.closeOverlay(overlay);
  }
}
