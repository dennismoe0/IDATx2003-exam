package no.ntnu.idatx2003.exam2025.boardgames.view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import no.ntnu.idatx2003.exam2025.boardgames.controller.DiceController;
import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;
import no.ntnu.idatx2003.exam2025.boardgames.model.Die;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.Command;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.RollDiceCommand;

/**
 * The class for making dice viewable in game.
 */
public class DiceView {
  private final BorderPane root;
  private final FlowPane diceView;
  private final Dice dice;
  private final List<DieView> dieViews = new ArrayList<>();
  private final Command command;

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
    DiceController diceController = new DiceController(dice);

    command = new RollDiceCommand(diceController);
    double gapSize = 10;

    root.prefHeight(height);
    root.prefWidth(width);

    List<Die> totalDice = dice.getDice();

    double numberOfDice = totalDice.size();
    double diceWidth = ((width / numberOfDice) / 2) - (numberOfDice * gapSize);
    DieView dieView;

    for (Die die : totalDice) {
      dieView = new DieView(die, diceWidth);
      dieViews.add(dieView);
      diceView.getChildren().add(dieView);
    }
    diceView.setOnMouseClicked(event -> {
      command.execute();
    });
    diceView.setVgap(10);
    diceView.setHgap(10);
    root.setCenter(diceView);

    root.getStyleClass().add("dice-view");
  }

  public Parent getRoot() {
    return root;
  }

  private void buildDiceView() {

  }

}
