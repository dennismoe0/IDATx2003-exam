package no.ntnu.idatx2003.exam2025.boardgames.view;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import no.ntnu.idatx2003.exam2025.boardgames.controller.BoardGameViewController;
import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;
import no.ntnu.idatx2003.exam2025.boardgames.util.view.AlertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for displaying the current board game.
 */
public class BoardGameView {
  private static final Logger log = LoggerFactory.getLogger(BoardGameView.class);
  private final Label title;
  private BorderPane view;
  private HBox content;
  private VBox rightMenu;
  private DiceView diceView;
  private MoveHistoryView moveHistoryView;
  private BoardView boardView;
  private BoardGameViewController controller;
  private BorderPane titleBar;
  private Rectangle rightMenuBackground;
  private StackPane rightMenuContainer;
  private Button takeTurnButton;
  private Label turnLabel;
  private AssetGamePieceView assetPieceView;
  private InsetPanel rollPanel;
  private InsetPanel moveHistoryPanel;

  /**
   * Default constructor for BoardGameView.
   *
   * @param title     a string representing the name of the Board Game
   *                  (Probably should be pulled from the game itself).
   * @param controller A controller for the view.
   */
  public BoardGameView(String title, BoardGameViewController controller) {
    this.controller = controller;
    createPanes();
    this.title = new Label(title);
    this.controller.getGameOverProperty().addListener(observable -> {
      AlertUtil.showInfo("Game Over!",
          this.controller.getWinnerName() + " has won the game!");
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
    rollPanel = new InsetPanel(300, 100);
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
    rightMenuBackground.getStyleClass().add("board-game-menu-background");

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
    try {
      diceView = new DiceView(controller.getDice(), 300, 200);
    } catch (NullPointerException e) {
      log.error(e.getMessage());
    }

    boardView = new BoardView(controller.getBoard());

    List<GamePiece> pieces = controller.getGamePieces();

    assetPieceView = new AssetGamePieceView(pieces, boardView.getTileViewRegister());
    boardView.addAssetGamePieceView(assetPieceView);

    turnLabel = new Label("Waiting for game to start");
    turnLabel.getStyleClass().add("h2");

    try {
      moveHistoryView = new MoveHistoryView(controller.getMoveHistory());
    } catch (NullPointerException e) {
      log.error(e.getMessage());
    }

    takeTurnButton = new Button("Take Turn");
    takeTurnButton.setOnAction(event -> {
      controller.takeTurn();
      updateTurnLabel(controller.getCurrentPlayerName());
    });

    rollPanel.getChildren().add(diceView.getRoot());

    rightMenu.getChildren().add(rollPanel);
    rightMenu.getChildren().add(takeTurnButton);
    rightMenu.getChildren().add(turnLabel);
    rightMenu.getChildren().add(moveHistoryView.getRoot());
  }

  private void updateTurnLabel(String text) {
    String turnInfo = "It is " + text + "'s turn";
    turnLabel.setText(turnInfo);
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
