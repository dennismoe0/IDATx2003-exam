package no.ntnu.idatx2003.exam2025.boardgames.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.google.gson.JsonObject;
import no.ntnu.idatx2003.exam2025.boardgames.model.GameSession;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.BoardFactory;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.BoardInfo;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderBoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneManager;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneRegister;
import no.ntnu.idatx2003.exam2025.boardgames.util.BoardInfoReader;
import no.ntnu.idatx2003.exam2025.boardgames.util.GsonFileReader;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.ChangeScreenCommand;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.OpenOverlayCommand;
import org.slf4j.Logger;

/**
 * Controller responsible for building and managing the setup of a board game
 * session,
 * including selecting games, boards, players, and starting the game.
 */
public class GameBuilderController {
  private static final Logger logger = Log.get(GameBuilderController.class);
  private GameSession gameSession;
  private String game;
  private BoardInfo board;
  private int numberOfDice;
  private BoardFactory boardFactory;
  private SceneRegister sceneRegister;
  private SceneManager sceneManager;
  private ChangeScreenCommand changeScreenCommand;
  private BoardInfoReader boardInfoReader;
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
      logger.info("Can't start game. Game, Board or Players are empty.");
      logger.info("Game, {}. Board: {}", game, board.getName());
      for (Player player : gameSession.getPlayers()) {
        logger.info("Player: {}", player.toString());
      }
      return;
    }
    gameSession.setBoardGame(buildGame());
    changeScreenCommand.execute();
  }

  /**
   * Adds a player to the current game session.
   *
   * @param player the player to add
   */
  public void addPlayer(Player player) {
    gameSession.addPlayer(player);
  }

  /**
   * Removes a player from the current game session.
   *
   * @param player the player to remove
   */
  public void removePlayer(Player player) {
    gameSession.removePlayer(player);
  }

  public void setNumberOfDice(int numberOfDice) {
    this.numberOfDice = numberOfDice;
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

  private LadderBoardGame buildGame() {
    String url = board.getUrl();
    GsonFileReader gson = new GsonFileReader();
    JsonObject readBoard;
    try {
      readBoard = gson.readJson(url);
      Board boardObject = boardFactory.buildBoardFromJson(readBoard);
      return new LadderBoardGame(boardObject, gameSession.getPlayers());
    } catch (IOException e) {
      logger.error(e.getMessage());
      return null;
    }
  }

  private void buildBoardInfoList(String game) {
    boardInfoList = boardInfoReader.getBoards(game);
  }
}
