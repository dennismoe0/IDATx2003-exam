package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Class for displaying the current board game.
 */
public class BoardGameView {
  private Text title;
  private BorderPane view;
  private VBox centerPanel;
  //board view
  //right panel
  //-queue/roll window
  //-current player turn
  //-log window
  //bottom panel

  public BoardGameView() {
    view = new BorderPane();
  }
}
