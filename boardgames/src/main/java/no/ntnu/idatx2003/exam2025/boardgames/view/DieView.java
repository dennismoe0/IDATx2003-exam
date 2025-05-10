package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import no.ntnu.idatx2003.exam2025.boardgames.model.Die;

/**
 * Die view for handling individual dice in the view.
 */
public class DieView extends StackPane {
  private final Text resultText = new Text();
  private final Rectangle dieView;
  private final Die die;
  private final double ARC_WIDTH = 7;

  /**
   * Default Constructor for the DieView.
   *
   * @param die a Die object.
   * @param widthHeight the dimensions the dice should take.
   */
  public DieView(Die die, double widthHeight) {
    super();
    dieView = new Rectangle(widthHeight, widthHeight, Color.BLUE);
    dieView.setArcHeight(widthHeight / ARC_WIDTH);
    dieView.setArcWidth(widthHeight / ARC_WIDTH);
    this.die = die;
    resultText.textProperty().bind(this.die.rollProperty().asString());
    resultText.setFill(Color.WHITE);
    this.getChildren().add(dieView);
    this.getChildren().add(resultText);
  }
}
