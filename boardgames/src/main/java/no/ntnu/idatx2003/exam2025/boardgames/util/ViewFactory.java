package no.ntnu.idatx2003.exam2025.boardgames.util;

import no.ntnu.idatx2003.exam2025.boardgames.model.GameSession;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.BoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderBoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.service.SceneManager;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.PrintLineCommand;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.StartGameCommand;
import no.ntnu.idatx2003.exam2025.boardgames.view.BoardGameView;
import no.ntnu.idatx2003.exam2025.boardgames.view.MenuOption;
import no.ntnu.idatx2003.exam2025.boardgames.view.MenuView;

import java.util.ArrayList;
import java.util.List;

public class ViewFactory {

  /**
   * Default method for making a LadderBoardGameView
   *
   * @param boardGame the game to be built.
   * @return returns a BoardGameView object.
   */
  public BoardGameView buildLadderBoardGameView(BoardGame boardGame) {
    return new BoardGameView("LadderBoardGame", (LadderBoardGame) boardGame);
  }

  public MenuView buildMainMenuView(GameSession gameSession, SceneManager sceneManager) {
    List<MenuOption> menuOptions = new ArrayList<>();
    menuOptions.add(new MenuOption("Start", new PrintLineCommand("Start"), true));
    menuOptions.add(new MenuOption(
        "Swap to Board View", new StartGameCommand(gameSession, sceneManager), true));
    menuOptions.add(new MenuOption("Settings", new PrintLineCommand("Settings"), true));
    menuOptions.add(new MenuOption("Players", new PrintLineCommand("Players"), true));
    menuOptions.add(new MenuOption("Exit", new PrintLineCommand("Exit"), true));
    return new MenuView("Main-Menu", menuOptions, 400,600,50);
  }
}

