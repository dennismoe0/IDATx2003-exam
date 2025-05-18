package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import no.ntnu.idatx2003.exam2025.boardgames.model.Die;

import java.util.Stack;

/**
 * Die view for handling individual dice in the view.
 */
public class DieView extends StackPane {
  private final Text resultText = new Text();
  private final Rectangle dieView;
  private final Die die;
  private final double ARC_WIDTH = 7;
  private GridPane dotLayout;
  private double sideLength;

  /**
   * Default Constructor for the DieView.
   *
   * @param die a Die object.
   * @param sideLength the dimensions the dice should take.
   */
  public DieView(Die die, double sideLength) {
    super();
    this.sideLength = sideLength;
    dieView = new Rectangle(sideLength, sideLength);
    this.die = die;
    dotLayout = new GridPane();
    //resultText.textProperty().bind(this.die.rollProperty().asString());
    this.die.rollProperty().addListener((observable, oldValue, newValue) -> {
      handleRoll(newValue.intValue());
    });
    this.getChildren().addAll(dieView, resultText, dotLayout);
    configureViews();
    resultText.setVisible(false);
    handleRoll(1);
    dieView.getStyleClass().add("die-view");
  }

  private void configureViews() {
    dieView.setArcHeight(sideLength / ARC_WIDTH);
    dieView.setArcWidth(sideLength / ARC_WIDTH);
    dotLayout.setMaxSize(sideLength, sideLength);
    buildDots();
  }

  private void buildDots() {
    ColumnConstraints column = new ColumnConstraints();
    column.setHgrow(Priority.ALWAYS);
    column.setPercentWidth(100.0 / 3);

    RowConstraints row = new RowConstraints();
    row.setVgrow(Priority.ALWAYS);
    row.setPercentHeight(100.0 / 3);

    dotLayout.getColumnConstraints().add(column);
    dotLayout.getRowConstraints().add(row);


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        // Resizing logic provided by AI / ChatGPT
        StackPane container = new StackPane();
        GridPane.setHgrow(container, Priority.ALWAYS);
        GridPane.setVgrow(container, Priority.ALWAYS);
        container.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        container.setMinSize(0, 0);

        Circle dot = new Circle();
        dot.radiusProperty().bind(Bindings.createDoubleBinding(
            () -> Math.min(container.getWidth(), container.getHeight() / 3),
            container.widthProperty(), container.heightProperty()));
        dot.getStyleClass().add("dice-dot");
        container.getChildren().add(dot);
        dotLayout.add(container, i, j);
      }
    }

  }


  private void handleRoll(int roll) {
    if (roll > 6) {
      resultText.setVisible(true);
    } else {
      resultText.setVisible(false);
      for (Node node : dotLayout.getChildren()) {
        node.setVisible(false);
      }
      switch (roll) {
        case 1:
          dotLayout.getChildren().get(4).setVisible(true);
          break;
        case 2:
          dotLayout.getChildren().get(3).setVisible(true);
          dotLayout.getChildren().get(5).setVisible(true);
          break;
        case 3:
          dotLayout.getChildren().get(0).setVisible(true);
          dotLayout.getChildren().get(4).setVisible(true);
          dotLayout.getChildren().get(8).setVisible(true);
          break;
        case 4:
          dotLayout.getChildren().get(0).setVisible(true);
          dotLayout.getChildren().get(2).setVisible(true);
          dotLayout.getChildren().get(6).setVisible(true);
          dotLayout.getChildren().get(8).setVisible(true);
          break;
        case 5:
          dotLayout.getChildren().get(0).setVisible(true);
          dotLayout.getChildren().get(2).setVisible(true);
          dotLayout.getChildren().get(4).setVisible(true);
          dotLayout.getChildren().get(6).setVisible(true);
          dotLayout.getChildren().get(8).setVisible(true);
          break;
        case 6:
          dotLayout.getChildren().get(0).setVisible(true);
          dotLayout.getChildren().get(2).setVisible(true);
          dotLayout.getChildren().get(3).setVisible(true);
          dotLayout.getChildren().get(5).setVisible(true);
          dotLayout.getChildren().get(6).setVisible(true);
          dotLayout.getChildren().get(8).setVisible(true);
      }
    }
  }

}
