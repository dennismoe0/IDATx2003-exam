package no.ntnu.idatx2003.exam2025.boardgames.view;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;
import no.ntnu.idatx2003.exam2025.boardgames.controller.GameBuilderController;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.BoardInfo;



/**
 * View for displaying options for populating and starting a game session.
 */
public class GameBuilderView {
  private final GameBuilderController controller;
  // player list
  // game chooser (drop down menu?)
  // player picker/color picker/icon picker?
  // Game rules (probably board based but whatever)
  // start button
  // board picker
  // backdrop

  private static final float height = 650;
  private static final float width = 1200;

  private final VBox boardColumn;
  private final VBox rulesColumn;
  private final VBox playerColumn;

  private final Label boardTitle;
  private final Label rulesTitle;
  private final Label playerTitle;
  private final Label gameHeader;
  private final Label boardHeader;

  private final HBox layout;
  private final Rectangle background;
  private final StackPane root;
  private final Button startGameButton;
  private final Button addNewPlayerButton;
  private final Button exitButton;

  private final ComboBox<String> gameMenu;
  private final ComboBox<BoardInfo> boardMenu;

  private final PlayerListView playerListView;

  /**
   * Constructor for the Game Builder View.
   *
   * @param controller     object that mediates between Game Builder and models
   * @param playerListView view that controls an updated list of players from database.
   */
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
    exitButton = new Button("Return to Main-Menu");
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
    // float columnWidth = width / 3;
    // Temporarily removed rules column.
    float columnWidth = width / 2;
    boardColumn.setMaxWidth(columnWidth);
    rulesColumn.setMaxWidth(columnWidth);
    playerColumn.setMaxWidth(columnWidth);
    layout.setAlignment(Pos.TOP_CENTER);
    layout.setPrefSize(width, height);

    boardColumn.setAlignment(Pos.TOP_CENTER);
    rulesColumn.setAlignment(Pos.TOP_CENTER);
    playerColumn.setAlignment(Pos.TOP_CENTER);

    StackPane listViewBackgroundWrapper = new StackPane();
    Rectangle listViewWrapperBackground = new Rectangle();
    listViewWrapperBackground.getStyleClass().add("menu-background");
    listViewWrapperBackground.widthProperty().bind(listViewBackgroundWrapper.widthProperty());
    listViewWrapperBackground.heightProperty().bind(listViewBackgroundWrapper.heightProperty());
    StackPane listViewWrapper = new StackPane();
    listViewWrapper.setPadding(new Insets(20));

    listViewBackgroundWrapper.getChildren().add(listViewWrapperBackground);
    listViewBackgroundWrapper.getChildren().add(listViewWrapper);
    listViewWrapper.getChildren().add(playerListView.getRoot());


    playerColumn.getChildren().addAll(playerTitle, listViewBackgroundWrapper);
    boardColumn.getChildren().addAll(boardTitle, gameHeader,
        gameMenu, boardHeader, boardMenu, startGameButton);
    rulesColumn.getChildren().add(rulesTitle);
    rulesColumn.getChildren().add(new Label("No rules selections available."));

    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(layout);
    HBox navbuttons = new HBox(10);
    navbuttons.getChildren().addAll(addNewPlayerButton, exitButton);
    navbuttons.setPadding(new Insets(20));
    navbuttons.setAlignment(Pos.CENTER);
    borderPane.setBottom(navbuttons);

    // layout.getChildren().addAll(playerColumn, boardColumn, rulesColumn);
    // Temporarily removed rules column.
    layout.getChildren().addAll(playerColumn, boardColumn, rulesColumn);
    root.getChildren().add(background);
    root.getChildren().add(borderPane);
  }

  private void configureMenus() {
    layout.setPadding(new Insets(20));

    gameMenu.setPromptText("Choose Game");
    boardMenu.setPromptText("Choose a board");

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
      resetBoardMenu();
      controller.selectBoard(null);
      updateBoardMenu();
    });

    boardMenu.setDisable(true);
    boardMenu.setOnAction((event) -> {
      BoardInfo selected = boardMenu.getSelectionModel().getSelectedItem();
      controller.selectBoard(selected);
    });
  }

  private void updateBoardMenu() {
    List<BoardInfo> boardInfoList = controller.getBoardInfoList();
    boardMenu.setDisable(boardInfoList.isEmpty());
    if (boardInfoList.isEmpty()) {
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

  private void resetBoardMenu() {
    boardMenu.getSelectionModel().clearSelection();
    boardMenu.setValue(null);
    boardMenu.getItems().clear();
    updateBoardMenu();
  }

  private void assignStyling() {
    background.getStyleClass().add("menu-background");
    background.getStyleClass().add("menu-red");
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

    exitButton.setOnAction(event -> {
      controller.returnToMainMenu();
    });
  }

  public Parent getRoot() {
    return root;
  }
}
