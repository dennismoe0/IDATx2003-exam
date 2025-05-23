package no.ntnu.idatx2003.exam2025.boardgames.controller;

import java.util.List;
import javafx.beans.property.BooleanProperty;
import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;
import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;
import no.ntnu.idatx2003.exam2025.boardgames.model.GameSession;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.BoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.DiceBoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderBoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderGameMoveHistory;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneManager;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneRegister;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.ChangeScreenCommand;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.ShowAlertCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller class for the BoardGameView.
 */
public class BoardGameController {
  private static final Logger log = LoggerFactory.getLogger(BoardGameController.class);
  private final BoardGame boardGame;
  private final GameSession gameSession;
  private final ChangeScreenCommand changeScreenCommand;
  private final ShowAlertCommand showAlertCommand;
  private LadderGameMoveHistory moveHistory;

  /**
   * Default Constructor for the BoardGameView controller.
   *
   * @param boardGame     a boardgame object representing the game.
   * @param gameSession   the game session the players are a part of.
   * @param sceneRegister the scene register for scene swapping.
   * @param sceneManager  the scene manager for scene swapping.
   */
  public BoardGameController(
      BoardGame boardGame, GameSession gameSession,
      SceneRegister sceneRegister, SceneManager sceneManager) {
    this.boardGame = boardGame;
    this.gameSession = gameSession;
    changeScreenCommand = new ChangeScreenCommand(sceneRegister, sceneManager, "main-menu");
    showAlertCommand = new ShowAlertCommand("Unfinished View", "Current view is unfinished");
    if (boardGame instanceof LadderBoardGame laddergame) {
      moveHistory = laddergame.getMoveHistory();
    }


  }

  public BooleanProperty getGameOverProperty() {
    return boardGame.getGameOverProperty();
  }

  public LadderGameMoveHistory getMoveHistory() {
    return moveHistory;
  }

  /**
   * Calls a take turn method on the board game.
   */
  public void takeTurn() {
    boardGame.takeTurn();
  }

  public String getCurrentPlayerName() {
    return boardGame.getCurrentPlayer().getPlayerName();
  }

  /**
   * Method for retrieving winner name for display in UI.
   *
   * @return a string representing the name of the winning player.
   */
  public String getWinnerName() {
    String name;
    try {
      name = boardGame.getWinner().getPlayerName();
      return name;
    } catch (NullPointerException e) {
      log.error(e.getMessage());
    }
    return null;
  }

  /**
   * Method for ending a game and returning to main menu.
   */
  public void exitToMainMenu() {
    gameSession.clearSession();
    changeScreenCommand.execute();
  }

  /**
   * Method for displaying a missing option alert.
   */
  public void showMissingOptionAlert() {
    showAlertCommand.execute();
  }

  public Board getBoard() {
    return boardGame.getBoard();
  }

  /**
   * Method for retrieving dice for the view.
   *
   * @return a dice object containing die objecs.
   */
  public Dice getDice() {
    if (boardGame instanceof DiceBoardGame dbg) {
      return dbg.getDice();
    }
    return null;
  }

  public List<GamePiece> getGamePieces() {
    return boardGame.getAllGamePieces();
  }


}
