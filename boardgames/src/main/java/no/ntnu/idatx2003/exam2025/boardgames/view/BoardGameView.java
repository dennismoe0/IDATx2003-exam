package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;

/**
 * Class for displaying the current board game.
 */
public class BoardGameView {
  private Text title;
  private BorderPane view;
  private HBox content;
  private VBox rightMenu;
  private DiceView diceView;
  private MoveHistoryView moveHistoryView;
  //board view
  //right panel
  //-queue/roll window
  //-current player turn
  //-log window
  //bottom panel

  public BoardGameView(String title) {
    this.title = new Text(title);
  }

  private void createPanes() {
    view = new BorderPane();
    content = new HBox();
    rightMenu = new VBox();
  }

}
