package no.ntnu.idatx2003.exam2025.laddersgamextreme;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.BoardModel;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.GameSession;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.Player;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.view.BoardView;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) {

    // Create a 10x10 board
    BoardModel tenBoard = new BoardModel(10, 10);

    GameSession gameSession = new GameSession(tenBoard);

    // Adds 5 players
    gameSession.addPlayer(new Player("Player1"), Color.GREEN);
    gameSession.addPlayer(new Player("Player2"), Color.BLUE);
    gameSession.addPlayer(new Player("Player3"), Color.RED);
    gameSession.addPlayer(new Player("Player4"), Color.YELLOW);
    gameSession.addPlayer(new Player("Player5"), Color.BLACK);

    // Placeholder special tiles
    tenBoard.setTileType(1, "START_TILE");
    tenBoard.setTileType(3, "LADDER_START");
    tenBoard.setTileType(15, "SNAKE_START");

    // Create BoardView with zigzag numbering, uses new gameSession instance
    BoardView boardViewTenBoard = new BoardView(tenBoard, gameSession);

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

    // Place button in an Hbox
    HBox controls = new HBox(10, minusButton, plusButton);
    controls.setAlignment(Pos.CENTER);

    // Arrange ui
    BorderPane root = new BorderPane();
    root.setCenter(boardViewTenBoard); // Board in the middle
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