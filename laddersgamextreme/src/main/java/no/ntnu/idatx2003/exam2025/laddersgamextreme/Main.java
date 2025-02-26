package no.ntnu.idatx2003.exam2025.laddersgamextreme;

import java.util.concurrent.ThreadLocalRandom;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.BoardModel;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.GameSession;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.Player;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.SpecialTile;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.service.SpecialTileManager;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.view.BoardView;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.view.SpecialTileView;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) {

    // Create a 9x10 board
    BoardModel tenBoard = new BoardModel(9, 10);

    GameSession gameSession = new GameSession(tenBoard);

    // Adds 5 players
    gameSession.addPlayer(new Player("Player1", 23), Color.GREEN);
    gameSession.addPlayer(new Player("Player2", 15), Color.BLUE);
    gameSession.addPlayer(new Player("Player3", 13), Color.RED);
    gameSession.addPlayer(new Player("Player4", 16), Color.YELLOW);
    gameSession.addPlayer(new Player("Player5", 123), Color.BLACK);

    SpecialTileManager specialTileManager = gameSession.getSpecialTileManager();

    // Start tile
    specialTileManager.addSpecialTile(new SpecialTile(1, 1, SpecialTile.TileType.START_TILE));

    // Finish tile
    int maxTile = tenBoard.getMaxTile();
    specialTileManager.addSpecialTile(new SpecialTile(
        maxTile, maxTile, SpecialTile.TileType.FINISH));

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

    // Create BoardView with zigzag numbering, uses new gameSession instance
    BoardView boardViewTenBoard = new BoardView(tenBoard, gameSession);

    Group specialTilesLayer = new Group();
    // iterate over all special tiles
    for (SpecialTile tile : specialTileManager.getAllSpecialTiles()) {
      SpecialTileView tileView = new SpecialTileView(
          tile, tenBoard, boardViewTenBoard.getTileSize());
      specialTilesLayer.getChildren().add(tileView);
    }

    // Buttons for testing movement before dice is implemented
    Button plusButton = new Button("+1");
    Button minusButton = new Button("-1");

    // When pressed player movement acts accordingly
    plusButton.setOnAction(event -> {
      gameSession.moveCurrentPlayer(1);
      boardViewTenBoard.refreshBoard();
    });

    minusButton.setOnAction(event -> {
      gameSession.moveCurrentPlayer(-1);
      boardViewTenBoard.refreshBoard();
    });

    // Temporary dice button, should be made into a class.
    // Should actually make the "controller" it's own class too.
    Button diceButton = new Button("Throw Dice");

    diceButton.setOnAction(event -> {
      int diceRoll = ThreadLocalRandom.current().nextInt(1, 7);
      gameSession.moveCurrentPlayer(diceRoll);
      boardViewTenBoard.refreshBoard();
    });

    // Place button in an Hbox
    HBox controls = new HBox(10, minusButton, diceButton, plusButton);
    controls.setAlignment(Pos.CENTER);

    StackPane boardContainer = new StackPane(boardViewTenBoard, specialTilesLayer);
    boardContainer.setAlignment(Pos.CENTER);

    // Arrange ui
    BorderPane root = new BorderPane();
    root.setCenter(boardContainer); // Board in the middle, DOESNT WORK?
    root.setBottom(controls); // Buttons at the bototm

    // Create a Scene and display it
    Scene scene = new Scene(root, 1000, 1000);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Snakes and Ladders Prototype");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}