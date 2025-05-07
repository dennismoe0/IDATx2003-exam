package no.ntnu.idatx2003.exam2025.boardgames.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;
import no.ntnu.idatx2003.exam2025.boardgames.model.Die;


/**
 * The class for making dice viewable in game.
 */
public class DiceView {
  private BorderPane root;
  private FlowPane diceView;
  private Dice dice;
  private List<DieView> dieViews = new ArrayList<>();

  /**
   * Default constructor.
   *
   * @param dice   the dice present in the Board game.
   * @param width  the initial width of the dice view pane.
   * @param height the initial height of the dice view pane.
   */
  public DiceView(Dice dice, double width, double height) {
    root = new BorderPane();
    diceView = new FlowPane();
    this.dice = dice;

    root.prefHeight(height);
    root.prefWidth(width);

    List<Die> totalDice = dice.getDice();

    double numberOfDice = totalDice.size();
    double diceWidth = width / numberOfDice;
    DieView dieView;

    for (Die die : totalDice) {
      dieView = new DieView(die, diceWidth);
      dieViews.add(dieView);
      diceView.getChildren().add(dieView);
    }

    root.setCenter(diceView);
  }

  private void buildDiceView(){

  }

}
