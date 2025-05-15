package no.ntnu.idatx2003.exam2025.boardgames.controller;

import no.ntnu.idatx2003.exam2025.boardgames.model.GameSession;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.BoardFactory;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderBoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneManager;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneRegister;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.ChangeScreenCommand;

public class GameBuilderController {
  private GameSession gameSession;
  private String game;
  private String board;
  private int numberOfDice;
  private BoardFactory boardFactory;
  private SceneRegister sceneRegister;
  private SceneManager sceneManager;
  private ChangeScreenCommand changeScreenCommand;


  public GameBuilderController(GameSession gameSession, SceneRegister sceneRegister, SceneManager sceneManager) {
    this.gameSession = gameSession;
    boardFactory = new BoardFactory();
    changeScreenCommand = new ChangeScreenCommand(
        sceneRegister, sceneManager, "ladder-game");
  }

  public void selectGame(String name) {
    game = name;
  }

  public void selectBoard(String name) {
    board = name;
  }

  public void startGame() {
    gameSession.setBoardGame(buildGame());
    changeScreenCommand.execute();
  }

  public void addPlayer(Player player) {
    gameSession.addPlayer(player);
  }

  public void removePlayer(Player player) {
    gameSession.removePlayer(player);
  }

  public void setNumberOfDice(int numberOfDice) {
    this.numberOfDice = numberOfDice;
  }

  public void openAddPlayerView(){
    ChangeScreenCommand command = new ChangeScreenCommand(sceneRegister, sceneManager, "add-player");
  }

  private LadderBoardGame buildGame() {
    Board board = boardFactory.createDefaultLadderBoard();
    LadderBoardGame game = new LadderBoardGame(board, gameSession.getPlayers());
    return game;
  }
}
