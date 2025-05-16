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

/**
 * View for displaying options for populating and starting a game session.
 */
public class GameBuilderView {
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
  private ComboBox<String> boardMenu;

  public GameBuilderView() {
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
    configureText();
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

    playerColumn.getChildren().addAll(playerTitle, addNewPlayerButton);
    boardColumn.getChildren().addAll(boardTitle, gameHeader,
        gameMenu, boardHeader, boardMenu, startGameButton);
    rulesColumn.getChildren().add(rulesTitle);

    layout.getChildren().addAll(playerColumn, boardColumn, rulesColumn);
    root.getChildren().add(background);
    root.getChildren().add(layout);
  }

  private void configureText() {
    gameMenu.setPlaceholder(new Label("Choose a game"));
    boardMenu.setPlaceholder(new Label("Choose a board"));
  }

  private void assignStyling() {
    background.getStyleClass().add("menu-background");
    boardTitle.getStyleClass().add("menu-title");
    rulesTitle.getStyleClass().add("menu-title");
    playerTitle.getStyleClass().add("menu-title");
  }

  public Parent getRoot() {
    return root;
  }
}
