package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;
import no.ntnu.idatx2003.exam2025.boardgames.controller.GameBuilderController;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.BoardInfo;

import java.util.List;

/**
 * View for displaying options for populating and starting a game session.
 */
public class GameBuilderView {
  private final GameBuilderController controller;
  //player list
  //game chooser (drop down menu?)
  //player picker/color picker/icon picker?
  //Game rules (probably board based but whatever)
  //start button
  //board picker
  //backdrop

  private static final float height = 650;
  private static final float width = 1200;

  private VBox boardColumn;
  private VBox rulesColumn;
  private VBox playerColumn;

  private Label boardTitle;
  private Label rulesTitle;
  private Label playerTitle;
  private Label gameHeader;
  private Label boardHeader;

  private HBox layout;
  private Rectangle background;
  private StackPane root;
  private Button startGameButton;
  private Button addNewPlayerButton;

  private ComboBox<String> gameMenu;
  private ComboBox<BoardInfo> boardMenu;

  private PlayerListView playerListView;

  public GameBuilderView(GameBuilderController controller, PlayerListView playerListView) {
    this.controller = controller;
    this.playerListView = playerListView;
    root = new StackPane();
    background = new Rectangle(width, height);
    boardColumn = new VBox(5);
    rulesColumn = new VBox(5);
    playerColumn = new VBox(5);
    layout = new HBox(10);
    startGameButton = new Button("Start");
    addNewPlayerButton = new Button("Add New Player");
    gameMenu = new ComboBox<>();
    boardMenu = new ComboBox<>();
    boardTitle = new Label("Board");
    rulesTitle = new Label("Rules");
    playerTitle = new Label("Player");
    gameHeader = new Label("Choose Game");
    boardHeader = new Label("Choose Board");
    configureView();
    configureMenus();
    configureButtons();
    assignStyling();
  }

  private void configureView() {
    root.setMaxSize(width, height);
    float columnWidth = width / 3;
    boardColumn.setMaxWidth(columnWidth);
    rulesColumn.setMaxWidth(columnWidth);
    playerColumn.setMaxWidth(columnWidth);
    layout.setAlignment(Pos.TOP_CENTER);
    layout.setPrefSize(width, height);

    boardColumn.setAlignment(Pos.TOP_CENTER);
    rulesColumn.setAlignment(Pos.TOP_CENTER);
    playerColumn.setAlignment(Pos.TOP_CENTER);

    playerColumn.getChildren().addAll(playerTitle, playerListView.getRoot(), addNewPlayerButton);
    boardColumn.getChildren().addAll(boardTitle, gameHeader,
        gameMenu, boardHeader, boardMenu, startGameButton);
    rulesColumn.getChildren().add(rulesTitle);

    layout.getChildren().addAll(playerColumn, boardColumn, rulesColumn);
    root.getChildren().add(background);
    root.getChildren().add(layout);
  }

  private void configureMenus() {
    gameMenu.setPlaceholder(new Label("Choose a game"));
    boardMenu.setPlaceholder(new Label("Choose a board"));

    gameMenu.getItems().add("Snakes 'n Ladders");
    gameMenu.getItems().add("Ludo");
    gameMenu.getItems().add("Den Forsvunne Diamanten");

    gameMenu.setOnAction((event) -> {
      int selectedIndex = gameMenu.getSelectionModel().getSelectedIndex();
      switch (selectedIndex) {
        case 0:
          controller.selectGame("ladder");
          break;
        case 1:
          controller.selectGame("ludo");
          break;
        case 2:
          controller.selectGame("diamanten");
          break;
        default:
          controller.selectGame("ladder");
      }
      controller.selectBoard(null);
      updateBoardMenu();
    });

    boardMenu.setOnAction((event) -> {
      BoardInfo selected = boardMenu.getSelectionModel().getSelectedItem();
      controller.selectBoard(selected);
    });
  }

  private void updateBoardMenu() {
    List<BoardInfo> boardInfoList = controller.getBoardInfoList();
    if (boardInfoList == null) {
      boardMenu.getItems().clear();
      return;
    }

    boardMenu.getItems().clear();
    boardMenu.getItems().addAll(boardInfoList);

    // Solution suggested by ChatGPT
    boardMenu.setConverter(new StringConverter<BoardInfo>() {
      @Override
      public String toString(BoardInfo object) {
        return object.getName();
      }

      @Override
      public BoardInfo fromString(String string) {
        return null;
      }
    });
  }

  private void assignStyling() {
    background.getStyleClass().add("menu-background");
    boardTitle.getStyleClass().add("menu-title");
    rulesTitle.getStyleClass().add("menu-title");
    playerTitle.getStyleClass().add("menu-title");
  }

  private void configureButtons() {
    addNewPlayerButton.setOnAction(event -> {
      controller.openAddPlayerView();
    });

    startGameButton.setOnAction(event -> {
      controller.startGame();
    });
  }

  public Parent getRoot() {
    return root;
  }
}
