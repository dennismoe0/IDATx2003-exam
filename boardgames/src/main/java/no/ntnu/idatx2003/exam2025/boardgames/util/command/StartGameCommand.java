package no.ntnu.idatx2003.exam2025.boardgames.util.command;

import no.ntnu.idatx2003.exam2025.boardgames.model.GameSession;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderBoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneManager;
import no.ntnu.idatx2003.exam2025.boardgames.util.ViewFactory;

public class StartGameCommand implements Command {
  private final GameSession gameSession;
  private final ViewFactory viewFactory;
  private final SceneManager sceneManager;

  public StartGameCommand(GameSession gameSession, SceneManager sceneManager) {
    this.gameSession = gameSession;
    viewFactory = new ViewFactory();
    this.sceneManager = sceneManager;
  }
  /**
   * Execute function used to perform whatever command needs to be performed.
   */
  @Override
  public void execute() {
    if (gameSession.getBoardGame() instanceof LadderBoardGame) {
      System.out.println("Start game command firing!");
      sceneManager.changeRoot(viewFactory.buildLadderBoardGameView(gameSession.getBoardGame()).asParent());
    }

  }
}
