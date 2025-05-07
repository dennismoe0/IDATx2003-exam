package no.ntnu.idatx2003.exam2025.boardgames.view;


import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import no.ntnu.idatx2003.exam2025.boardgames.model.Die;

/**
 * Die view for handling individual dice in the view.
 */
public class DieView extends Rectangle {
  private final Text resultText = new Text();
  private final Die die;

  public DieView(Die die, double widthHeight) {
    this.die = die;
    this.setWidth(widthHeight);
    this.setHeight(widthHeight);
    this.setStyle("-fx-background-color: purple;");
  }
}
