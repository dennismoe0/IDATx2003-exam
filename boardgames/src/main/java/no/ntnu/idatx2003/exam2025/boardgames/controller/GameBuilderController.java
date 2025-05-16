package no.ntnu.idatx2003.exam2025.boardgames.controller;

import no.ntnu.idatx2003.exam2025.boardgames.model.GameSession;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.BoardFactory;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderBoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneManager;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneRegister;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.ChangeScreenCommand;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.OpenOverlayCommand;

/**
 * Controller responsible for building and managing the setup of a board game
 * session,
 * including selecting games, boards, players, and starting the game.
 */
public class GameBuilderController {

  private GameSession gameSession;
  private String game;
  private String board;
  private int numberOfDice;
  private BoardFactory boardFactory;
  private SceneRegister sceneRegister;
  private SceneManager sceneManager;
  private ChangeScreenCommand changeScreenCommand;

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
  }

  /**
   * Selects the game by its name.
   *
   * @param name the name of the game to select
   */
  public void selectGame(String name) {
    game = name;
  }

  /**
   * Selects the board by its name.
   *
   * @param name the name of the board to select
   */
  public void selectBoard(String name) {
    board = name;
  }

  /**
   * Starts the game by building the selected game and changing the screen.
   */
  public void startGame() {
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

  private LadderBoardGame buildGame() {
    Board board = boardFactory.createDefaultLadderBoard();
    LadderBoardGame game = new LadderBoardGame(board, gameSession.getPlayers());
    return game;
  }
}
