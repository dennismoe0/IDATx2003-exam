package no.ntnu.idatx2003.exam2025.boardgames.controller;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.List;
import no.ntnu.idatx2003.exam2025.boardgames.model.GameSession;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.BoardFactory;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.BoardInfo;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.BoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderBoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneManager;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneRegister;
import no.ntnu.idatx2003.exam2025.boardgames.util.BoardInfoReader;
import no.ntnu.idatx2003.exam2025.boardgames.util.GsonFileReader;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.ChangeScreenCommand;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.OpenOverlayCommand;
import no.ntnu.idatx2003.exam2025.boardgames.util.view.AlertUtil;
import org.slf4j.Logger;

/**
 * Controller responsible for building and managing the setup of a board game
 * session,
 * including selecting games, boards, players, and starting the game.
 */
public class GameBuilderController {
  private static final Logger logger = Log.get(GameBuilderController.class);
  private final GameSession gameSession;
  private String game;
  private BoardInfo board;
  private int numberOfDice;
  private final BoardFactory boardFactory;
  private final SceneRegister sceneRegister;
  private final SceneManager sceneManager;
  private final ChangeScreenCommand changeScreenCommand;
  private final BoardInfoReader boardInfoReader;
  private List<BoardInfo> boardInfoList;

  /**
   * Constructs a GameBuilderController with the given game session, scene
   * register, and scene manager.
   *
   * @param gameSession   the current game session
   * @param sceneRegister the scene register
   * @param sceneManager  the scene manager
   */
  public GameBuilderController(
      GameSession gameSession,
      SceneRegister sceneRegister,
      SceneManager sceneManager) {
    this.gameSession = gameSession;
    boardFactory = new BoardFactory();
    this.sceneRegister = sceneRegister;
    this.sceneManager = sceneManager;
    changeScreenCommand = new ChangeScreenCommand(
        sceneRegister, sceneManager, "ladder-game");
    boardInfoReader = new BoardInfoReader(
        "src/main/resources/assets/boards/laddergameboards/BoardList.json");
    numberOfDice = 2;
  }

  /**
   * Selects the game by its name.
   *
   * @param name the name of the game to select
   */
  public void selectGame(String name) {
    game = name;
    buildBoardInfoList(game);
  }

  /**
   * Selects the board by its name.
   *
   * @param board the name of the board to select
   */
  public void selectBoard(BoardInfo board) {
    this.board = board;
  }

  /**
   * Starts the game by building the selected game and changing the screen.
   */
  public void startGame() {
    if (game == null || board == null || gameSession.getPlayers().isEmpty()) {
      AlertUtil.showError("Missing Items",
          "Please ensure you've selected your players, game, and board before starting the game.");
      return;
    }

    BoardGame game = buildGame();
    if (game == null) {
      AlertUtil.showError("Something went Wrong", "No Board Game Detected");
      return;
    }
    gameSession.setBoardGame(game);

    changeScreenCommand.execute();
  }

  /**
   * Method for manually setting number of dice to use in game.
   *
   * @param input a string representing an integer.
   * @throws Exception throws an exception when an int cannot be parsed.
   */
  public void setNumberOfDice(String input) throws Exception {
    try {
      this.numberOfDice = Integer.parseInt(input);
    } catch (NumberFormatException e) {
      throw new Exception("Incorrect dice Format");
    }
  }

  public int getNumberOfDice() {
    return numberOfDice;
  }

  /**
   * Opens the overlay view for adding a new player.
   */
  public void openAddPlayerView() {
    OpenOverlayCommand command = new OpenOverlayCommand(sceneRegister, sceneManager, "add-player");
    command.execute();
  }

  /**
   * A method for retrieving boards for the currently selected boardgames, to be displayed.
   *
   * @return returns a list of BoardInfo objects.
   */
  public List<BoardInfo> getBoardInfoList() {
    if (game == null) {
      return null;
    }
    return boardInfoList;
  }

  /**
   * A command for leaving the current view and returning to the main menu.
   */
  public void returnToMainMenu() {
    ChangeScreenCommand command =
        new ChangeScreenCommand(sceneRegister, sceneManager, "main-menu");
    gameSession.clearSession();
    command.execute();
  }

  private BoardGame buildGame() {
    String url = board.getUrl();
    GsonFileReader gson = new GsonFileReader();
    JsonObject readBoard;

    switch (game) {
      case "ladder":
        try {
          readBoard = gson.readJson(url);
          Board boardObject = boardFactory.buildBoardFromJson(readBoard);
          try {
            return new LadderBoardGame(numberOfDice, boardObject, gameSession.getPlayers());
          } catch (Exception e) {
            AlertUtil.showError("Error Encountered", e.getMessage());
            return null;
          }
        } catch (IOException e) {
          logger.error(e.getMessage());
          return null;
        }
      case "ludo":
        return null;
      case "diamanten":
        return null;
      default:
        return null;
    }
  }

  private void buildBoardInfoList(String game) {
    boardInfoList = boardInfoReader.getBoards(game);
  }
}
