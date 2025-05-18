package no.ntnu.idatx2003.exam2025.boardgames.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.Parent;
import no.ntnu.idatx2003.exam2025.boardgames.controller.AddPlayerViewController;
import no.ntnu.idatx2003.exam2025.boardgames.controller.GameBuilderController;
import no.ntnu.idatx2003.exam2025.boardgames.controller.PlayerListViewController;
import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.model.GameSession;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.BoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderBoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneManager;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneRegister;
import no.ntnu.idatx2003.exam2025.boardgames.service.StatsManager;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.ChangeScreenCommand;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.PrintLineCommand;
import no.ntnu.idatx2003.exam2025.boardgames.view.*;
import org.slf4j.Logger;

/**
 * Factory class for building various views used in the board games application.
 */
public class ViewFactory {

  private static final Logger logger = Log.get(ViewFactory.class);

  /**
   * Default method for making a LadderBoardGameView.
   *
   * @param boardGame the game to be built.
   * @return returns a BoardGameView object.
   */
  public Parent buildLadderBoardGameView(BoardGame boardGame) {
    logger.info("Building ladder board game");
    BoardGameView boardGameView = new BoardGameView("LadderBoardGame", (LadderBoardGame) boardGame);
    return boardGameView.asParent();
  }

  /**
   * Builds the main menu view.
   *
   * @param sceneRegister the scene register
   * @param sceneManager  the scene manager
   * @return the root node of the main menu view
   */
  public Parent buildMainMenuView(SceneRegister sceneRegister, SceneManager sceneManager) {
    logger.info("Building main menu");
    List<MenuOption> menuOptions = new ArrayList<>();
    menuOptions.add(new MenuOption("Start",
        new ChangeScreenCommand(sceneRegister, sceneManager, "build-game"), true));
    menuOptions.add(new MenuOption(
        "Swap to Board View", new ChangeScreenCommand(
            sceneRegister, sceneManager, "ladder-game"),
        true));
    menuOptions.add(new MenuOption("Settings", new PrintLineCommand("Settings"), true));
    menuOptions.add(new MenuOption("Players", new PrintLineCommand("Players"), true));
    menuOptions.add(new MenuOption("Exit", new PrintLineCommand("Exit"), true));
    MenuView menu = new MenuView("Main Menu", menuOptions, 400, 600, 50);
    return menu.asParent();
  }

  /**
   * Builds the add player view.
   *
   * @param gameSession  the current game session
   * @param sceneManager the scene manager
   * @param playerDao    the player DAO implementation
   * @return the root node of the add player view
   */
  public Parent buildAddPlayerView(
      GameSession gameSession,
      SceneManager sceneManager,
      PlayerDaoImpl playerDao) {
    logger.info("Building Add Player view");
    AddPlayerViewController addPlayerViewController = new AddPlayerViewController(
        gameSession, sceneManager, playerDao);
    AddPlayerView view = new AddPlayerView(addPlayerViewController);
    return view.getRoot();
  }

  /**
   * Builds the game builder view.
   *
   * @param gameSession   the current game session
   * @param sceneManager  the scene manager
   * @param sceneRegister the scene register
   * @return the root node of the game builder view
   */

  public Parent buildGameBuilderView(
      GameSession gameSession,
      SceneManager sceneManager,
      SceneRegister sceneRegister,
      PlayerDaoImpl playerDao,
      Map<String, StatsManager<?>> statsManagers) {
    PlayerListView playerListView = new PlayerListView(
        new PlayerListViewController(playerDao, statsManagers, gameSession));
    GameBuilderView gbView = new GameBuilderView(
        new GameBuilderController(
            gameSession,
            sceneRegister,
            sceneManager),
            playerListView);
    return gbView.getRoot();
  }

  /**
   * Builds the player list view.
   *
   * @param playerDao     the player DAO implementation
   * @param statsManagers the map of stats managers
   * @return the root node of the player list view
   */
  public Parent buildPlayerListView(
      PlayerDaoImpl playerDao,
      Map<String, StatsManager<?>> statsManagers, GameSession gameSession) {
    PlayerListViewController controller = new PlayerListViewController(playerDao, statsManagers, gameSession);
    PlayerListView view = new PlayerListView(controller);
    return view.getRoot();
  }
}
