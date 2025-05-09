package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.BoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderBoardGame;

/**
 * Class for displaying the current board game.
 */
public class BoardGameView {
  private Text title;
  private BorderPane view;
  private HBox content;
  private VBox rightMenu;
  private DiceView diceView;
  private MoveHistoryView moveHistoryView;
  private BoardView boardView;
  private LadderBoardGame ladderBoardGame;
  //BoardViewController
  //DiceView controller

  /**
   * Default constructor for BoardGameView
   *
   * @param title     a string representing the name of the Board Game
   *                  (Probably should be pulled from the game itself).
   * @param boardGame A board game object representing the current game.
   */
  public BoardGameView(String title, LadderBoardGame boardGame) {
    createPanes();
    this.title = new Text(title);
    this.ladderBoardGame = boardGame;
    createViews();
    configurePanes();
  }

  private void createPanes() {
    view = new BorderPane();
    content = new HBox(10);
    rightMenu = new VBox();
  }

  private void configurePanes() {
    view.setCenter(content);
    content.getChildren().add(boardView.asParent());
    content.getChildren().add(rightMenu);
    rightMenu.prefHeight(750);
    rightMenu.prefWidth(400);
    view.setTop(title);
  }

  private void createViews() {
    diceView = new DiceView(ladderBoardGame.getDice(), 300, 200);
    boardView = new BoardView(ladderBoardGame.getBoard());
    moveHistoryView = new MoveHistoryView(ladderBoardGame.getMoveHistory());
    Button takeTurnButton = new Button("Take Turn");
    takeTurnButton.setOnAction(event -> ladderBoardGame.takeTurn());
    rightMenu.getChildren().add(diceView.getRoot());
    rightMenu.getChildren().add(takeTurnButton);
    rightMenu.getChildren().add(new Label("Player name goes here"));
    rightMenu.getChildren().add(moveHistoryView.getRoot());
  }

  /**
   * The default method for getting the current view to be used as a scene root.
   *
   * @return returns a Border Pane node object for use with JavaFX scenes.
   */
  public Parent asParent() {
    return view;
  }

}
