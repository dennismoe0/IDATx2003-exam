package no.ntnu.idatx2003.exam2025.ludo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Group root = new Group();
    Scene scene = new Scene(root,Color.WHITESMOKE);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Ludo");
    primaryStage.setWidth(800);
    primaryStage.setHeight(800);

    Button button = new Button();
    button.setText("Switch Scene");
    button.setOnAction(e -> {
      primaryStage.setScene(new Scene(new Group(), Color.BLACK));
    });



    root.getChildren().add(button);
    primaryStage.setResizable(false);
    primaryStage.show();

  }
}
