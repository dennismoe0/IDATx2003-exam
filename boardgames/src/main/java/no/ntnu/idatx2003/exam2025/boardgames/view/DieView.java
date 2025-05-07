package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import no.ntnu.idatx2003.exam2025.boardgames.model.Die;

/**
 * Die view for handling individual dice in the view.
 */
public class DieView extends Rectangle {
  private final Text resultText = new Text();
  private final Die die;
  private final double ARC_WIDTH = 7;

  public DieView(Die die, double widthHeight) {
    super(widthHeight, widthHeight, Color.BLUE);
    super.setArcHeight(widthHeight / ARC_WIDTH);
    super.setArcWidth(widthHeight / ARC_WIDTH);
    this.die = die;
    this.resultText.setText("Hello");
  }
}
