package no.ntnu.idatx2003.exam2025.boardgames.view;


import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderBoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.util.view.AlertUtil;

/**
 * Class for displaying the current board game.
 */
public class BoardGameView {
  private final Label title;
  private BorderPane view;
  private HBox content;
  private VBox rightMenu;
  private DiceView diceView;
  private MoveHistoryView moveHistoryView;
  private BoardView boardView;
  private LadderBoardGame ladderBoardGame;
  private BorderPane titleBar;
  private Rectangle rightMenuBackground;
  private StackPane rightMenuContainer;
  private Button takeTurnButton;

  /**
   * Default constructor for BoardGameView.
   *
   * @param title     a string representing the name of the Board Game
   *                  (Probably should be pulled from the game itself).
   * @param boardGame A board game object representing the current game.
   */
  public BoardGameView(String title, LadderBoardGame boardGame) {
    if (boardGame == null) {
      throw new IllegalArgumentException("LadderBoardGame cant be null");
    }
    createPanes();
    this.title = new Label(title);
    this.ladderBoardGame = boardGame;
    this.ladderBoardGame.getGameOverProperty().addListener(observable -> {
      AlertUtil.showInfo("Game Over!",
          ladderBoardGame.getWinner().getPlayerName() + " has won the game!");
      takeTurnButton.setDisable(true);
    });

    createViews();
    configurePanes();
  }

  private void createPanes() {
    view = new BorderPane();
    content = new HBox(30);
    rightMenu = new VBox(30);
    titleBar = new BorderPane();
    rightMenuBackground = new Rectangle();
    rightMenuContainer = new StackPane();
  }

  private void configurePanes() {
    view.setCenter(content);
    rightMenuContainer.prefHeight(500);
    rightMenuContainer.prefWidth(300);
    rightMenuContainer.setMaxHeight(600);
    rightMenu.setPrefWidth(300);
    rightMenu.setMaxHeight(550);
    rightMenu.setMaxWidth(300);

    rightMenuBackground.heightProperty().bind(rightMenuContainer.heightProperty());
    rightMenuBackground.widthProperty().bind(rightMenuContainer.widthProperty());
    rightMenuContainer.getChildren().add(rightMenuBackground);
    rightMenuContainer.getChildren().add(rightMenu);
    rightMenu.setPadding(new Insets(20));

    content.getChildren().add(boardView.asParent());
    content.getChildren().add(rightMenuContainer);

    titleBar.setPrefHeight(75);
    titleBar.setCenter(title);
    titleBar.getStyleClass().add("title-bar");
    title.getStyleClass().add("title-bar");
    rightMenuContainer.getStyleClass().add("board-game-sidebar");
    rightMenuBackground.getStyleClass().add("board-game-sidebar");

    StackPane.setAlignment(rightMenu, Pos.CENTER);

    buildMainView();
    view.setTop(titleBar);

    view.getStyleClass().add("board-game-view");
  }

  private void buildMainView() {
    Region left = new Region();
    Region right = new Region();
    BorderPane.setAlignment(right, Pos.CENTER);
    BorderPane.setAlignment(left, Pos.CENTER);
    left.setMinWidth(0);
    left.setMinHeight(0);
    right.setMinWidth(0);
    right.setMinHeight(0);
    left.setPrefWidth(150);
    right.setPrefWidth(150);
    left.setPrefHeight(750);
    right.setPrefHeight(750);

    view.setLeft(left);
    view.setRight(right);
  }

  private void createViews() {
    diceView = new DiceView(ladderBoardGame.getDice(), 300, 200);
    boardView = new BoardView(ladderBoardGame.getBoard());

    List<GamePiece> pieces = ladderBoardGame.getAllGamePieces();
    GamePieceView pieceView = new GamePieceView(pieces, boardView.getTileViewRegister());
    boardView.addGamePieceView(pieceView);

    moveHistoryView = new MoveHistoryView(ladderBoardGame.getMoveHistory());
    takeTurnButton = new Button("Take Turn");
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
