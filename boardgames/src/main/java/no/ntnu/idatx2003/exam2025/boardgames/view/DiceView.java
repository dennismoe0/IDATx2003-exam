package no.ntnu.idatx2003.exam2025.boardgames.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;

/**
 * The class for making dice viewable in game.
 */
public class DiceView {
  private BorderPane root;
  private FlowPane diceView;
  private Dice dice;

  public DiceView(Dice dice) {
    root = new BorderPane();
    diceView = new FlowPane();
    this.dice = dice;

  }

  private void buildDiceView(){

  }

}
