package no.ntnu.idatx2003.exam2025.laddersgamextreme;

import java.util.concurrent.ThreadLocalRandom;

import java.sql.Connection;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.BoardModel;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.GameSession;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.Player;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.SpecialTile;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.service.DatabaseManager;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.service.PlayerManager;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.service.SpecialTileManager;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.view.BoardView;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.view.SpecialTileView;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) {

    //Create the main menu root.
    Pane mainMenuRoot = new Pane();
    Scene mainScene = new Scene(mainMenuRoot, 800, 600);
    primaryStage.setScene(mainScene);
    primaryStage.setTitle("Board Games");
    primaryStage.show();
  }


  public Pane createMainMenu(){
    Pane mainMenu = new Pane();
    VBox vBox = new VBox();
    mainMenu.getChildren().add(vBox);

    Label titleText = new Label("Main Menu");
    Button gameButton1 = new Button("Snakes n Ladders");
    Button gameButton2 = new Button("Ludo");
    Button gameButton3 = new Button("Yahtzee");
    Label settingsText = new Label("Settings and Statistics");
    Button buttonSettings = new Button("Settings");
    Button buttonStatistics = new Button("Statistics");
    Button buttonExit = new Button("Exit");

    vBox.getChildren().add(titleText);
    vBox.getChildren().add(gameButton1);
    vBox.getChildren().add(gameButton2);
    vBox.getChildren().add(gameButton3);
    vBox.getChildren().add(settingsText);
    vBox.getChildren().add(buttonSettings);
    vBox.getChildren().add(buttonStatistics);
    vBox.getChildren().add(buttonExit);

    return mainMenu;
  }
  public void runSnakesAndLadders(Stage primaryStage){
    // Initialize the database
    DatabaseManager.initializeDatabase();

    try (Connection connection = DatabaseManager.connect()) {
      PlayerManager playerManager = new PlayerManager(connection);

      // Retrieve or create players
      Player player1 = playerManager.getOrCreatePlayer("Player1", 23);
      Player player2 = playerManager.getOrCreatePlayer("Player2", 15);
      Player player3 = playerManager.getOrCreatePlayer("Player3", 13);
      Player player4 = playerManager.getOrCreatePlayer("Player4", 16);
      Player player5 = playerManager.getOrCreatePlayer("Player5", 123);

      // Create a 9x10 board
      BoardModel tenBoard = new BoardModel(9, 10);
      GameSession gameSession = new GameSession(tenBoard);

      // Add players to GameSession using stored data
      gameSession.addPlayer(player1, Color.GREEN);
      gameSession.addPlayer(player2, Color.BLUE);
      gameSession.addPlayer(player3, Color.RED);
      gameSession.addPlayer(player4, Color.YELLOW);
      gameSession.addPlayer(player5, Color.BLACK);


      SpecialTileManager specialTileManager = gameSession.getSpecialTileManager();

      // Start tile
      specialTileManager.addSpecialTile(new SpecialTile(1, 1, SpecialTile.TileType.START_TILE));

      // Finish tile
      int maxTile = tenBoard.getMaxTile();
      specialTileManager.addSpecialTile(new SpecialTile(maxTile, maxTile, SpecialTile.TileType.FINISH));

      // Ladders
      specialTileManager.addSpecialTile(new SpecialTile(1, 40, SpecialTile.TileType.LADDER));
      specialTileManager.addSpecialTile(new SpecialTile(8, 10, SpecialTile.TileType.LADDER));
      specialTileManager.addSpecialTile(new SpecialTile(36, 52, SpecialTile.TileType.LADDER));
      specialTileManager.addSpecialTile(new SpecialTile(43, 62, SpecialTile.TileType.LADDER));
      specialTileManager.addSpecialTile(new SpecialTile(49, 79, SpecialTile.TileType.LADDER));
      specialTileManager.addSpecialTile(new SpecialTile(65, 82, SpecialTile.TileType.LADDER));
      specialTileManager.addSpecialTile(new SpecialTile(68, 85, SpecialTile.TileType.LADDER));

      // Snakes
      specialTileManager.addSpecialTile(new SpecialTile(24, 5, SpecialTile.TileType.SNAKE));
      specialTileManager.addSpecialTile(new SpecialTile(33, 3, SpecialTile.TileType.SNAKE));
      specialTileManager.addSpecialTile(new SpecialTile(42, 30, SpecialTile.TileType.SNAKE));
      specialTileManager.addSpecialTile(new SpecialTile(56, 37, SpecialTile.TileType.SNAKE));
      specialTileManager.addSpecialTile(new SpecialTile(64, 27, SpecialTile.TileType.SNAKE));
      specialTileManager.addSpecialTile(new SpecialTile(74, 12, SpecialTile.TileType.SNAKE));
      specialTileManager.addSpecialTile(new SpecialTile(87, 70, SpecialTile.TileType.SNAKE));

      // Create BoardView with zigzag numbering
      BoardView boardViewTenBoard = new BoardView(tenBoard, gameSession);
      Group specialTilesLayer = new Group();

      for (SpecialTile tile : specialTileManager.getAllSpecialTiles()) {
        SpecialTileView tileView = new SpecialTileView(tile, tenBoard, boardViewTenBoard.getTileSize());
        specialTilesLayer.getChildren().add(tileView);
      }

      // Buttons for testing movement
      Button plusButton = new Button("+1");
      Button minusButton = new Button("-1");

      plusButton.setOnAction(event -> {
        gameSession.moveCurrentPlayer(1);
        boardViewTenBoard.refreshBoard();
      });

      minusButton.setOnAction(event -> {
        gameSession.moveCurrentPlayer(-1);
        boardViewTenBoard.refreshBoard();
      });

      Button diceButton = new Button("Throw Dice");

      diceButton.setOnAction(event -> {
        int diceRoll = (int) (Math.random() * 6) + 1;
        gameSession.moveCurrentPlayer(diceRoll);
        boardViewTenBoard.refreshBoard();
      });

      HBox controls = new HBox(10, minusButton, diceButton, plusButton);
      controls.setAlignment(Pos.CENTER);

      StackPane boardContainer = new StackPane(boardViewTenBoard, specialTilesLayer);
      boardContainer.setAlignment(Pos.CENTER);

      BorderPane root = new BorderPane();
      root.setCenter(boardContainer);
      root.setBottom(controls);

      Scene scene = new Scene(root, 1000, 1000);
      primaryStage.setScene(scene);
      primaryStage.setTitle("Snakes and Ladders Prototype");


      primaryStage.show();

    } catch (Exception e) {
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}