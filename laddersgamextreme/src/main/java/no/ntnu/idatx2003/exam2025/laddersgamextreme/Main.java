package no.ntnu.idatx2003.exam2025.laddersgamextreme;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.model.BoardModel;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.view.BoardView;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) {

    // Create a 10x10 board
    BoardModel tenBoard = new BoardModel(10, 10);

    // Placeholder special tiles
    tenBoard.setTileType(1, "START_TILE");
    tenBoard.setTileType(3, "LADDER_START");
    tenBoard.setTileType(15, "SNAKE_START");

    // Create BoardView with zigzag numbering
    BoardView boardViewTenBoard = new BoardView(tenBoard);

    // Create a Scene and display it
    Scene scene = new Scene(boardViewTenBoard, 1000, 1000);
    primaryStage.setTitle("Snakes and Ladders Prototype");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}