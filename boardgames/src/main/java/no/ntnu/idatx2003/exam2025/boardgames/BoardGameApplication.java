package no.ntnu.idatx2003.exam2025.boardgames;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;
import no.ntnu.idatx2003.exam2025.boardgames.model.Die;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.BoardFactory;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderBoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderGameMoveHistory;
import no.ntnu.idatx2003.exam2025.boardgames.util.GsonFileReader;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.PrintLineCommand;
import no.ntnu.idatx2003.exam2025.boardgames.view.*;


/**
 * The main Application File for starting and stopping the program.
 */
public class BoardGameApplication extends Application {


  @Override
  public void start(Stage primaryStage) throws Exception {
    MenuView view = new MenuView("Main Menu", buildTestMenu());
    BoardFactory factory = new BoardFactory();
    GsonFileReader reader = new GsonFileReader();
    Board board = factory.buildBoardFromJson(reader.readJson(
        "src/main/resources/assets/boards/laddergameboards/laddergame_classic.json"));
    BoardView boardView = new BoardView(board);


    Dice dice = new Dice();
    dice.addDice(new Die(6));
    dice.addDice(new Die(6));
    DiceView diceView = new DiceView(dice, 300, 400);


    LadderGameMoveHistory moveHistory = new LadderGameMoveHistory();
    MoveHistoryView moveHistoryView = new MoveHistoryView(moveHistory);


    List<Player> players = new ArrayList<Player>();
    Player player1 = new Player(1, "Dennis", 24);
    players.add(player1);
    Player player2 = new Player(2, "Sasha", 27);
    players.add(player2);

    LadderBoardGame ladderBoardGame = new LadderBoardGame(board, players);
    BoardGameView boardGameView = new BoardGameView("Snake's n Ladders", ladderBoardGame);


    //Scene scene = new Scene(view.asParent(), 400, 400);
    //Scene scene = new Scene(boardView.asParent(), 600, 600);
    //Scene scene = new Scene(diceView.getRoot(), 300, 400);
    //Scene scene = new Scene(moveHistoryView.getRoot(), 400, 500);
    Scene scene = new Scene(boardGameView.asParent(), 1200, 750);
    scene.getStylesheets().add(
        getClass().getResource("/assets/style/styles.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * The main method for running the program.
   *
   * @param args default args.
   */
  public static void main(String[] args) {
    launch(args);
  }

  private List<MenuOption> buildTestMenu() {
    List<MenuOption> menuOptions = new ArrayList<>();
    menuOptions.add(new MenuOption("Start", new PrintLineCommand("Start")));
    menuOptions.add(new MenuOption("Settings", new PrintLineCommand("Settings")));
    menuOptions.add(new MenuOption("Players", new PrintLineCommand("Players")));
    menuOptions.add(new MenuOption("Exit", new PrintLineCommand("Exit")));
    return menuOptions;
  }
}
