package no.ntnu.idatx2003.exam2025.boardgames.view;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import no.ntnu.idatx2003.exam2025.boardgames.controller.DiceController;
import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;
import no.ntnu.idatx2003.exam2025.boardgames.model.Die;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.Command;
import no.ntnu.idatx2003.exam2025.boardgames.util.command.RollDiceCommand;


/**
 * The class for making dice viewable in game.
 */
public class DiceView {
  private final StackPane root;
  private final HBox diceView;
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
    root = new StackPane();
    diceView = new HBox(20);
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
    diceView.setAlignment(Pos.CENTER);
    root.getChildren().add(diceView);
    StackPane.setAlignment(diceView, Pos.CENTER);

    root.getStyleClass().add("dice-view");
  }


  public Parent getRoot() {
    return root;
  }

  private void buildDiceView(){

  }

}
