package no.ntnu.idatx2003.exam2025.boardgames.view;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import no.ntnu.idatx2003.exam2025.boardgames.controller.BoardGameController;
import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;
import no.ntnu.idatx2003.exam2025.boardgames.model.Die;

/**
 * The class for making dice viewable in game.
 */
public class DiceView {
  private final StackPane root;
  private final HBox diceView;
  private final Dice dice;
  private final List<DieView> dieViews = new ArrayList<>();
  private final BoardGameController controller;
  private final double width;
  private final double height;

  /**
   * Default constructor.
   *
   * @param dice   the dice present in the Board game.
   * @param width  the initial width of the dice view pane.
   * @param height the initial height of the dice view pane.
   */
  public DiceView(Dice dice, double width, double height, BoardGameController controller) {
    root = new StackPane();
    diceView = new HBox(20);
    this.dice = dice;
    double gapSize = 10;
    this.controller = controller;
    this.width = width;
    this.height = height;
    buildDiceView();
    configureView();
    root.getChildren().add(diceView);
    StackPane.setAlignment(diceView, Pos.CENTER);

    root.getStyleClass().add("dice-view");
  }

  private void configureView() {
    root.prefHeight(height);
    root.prefWidth(width);
  }

  public Parent getRoot() {
    return root;
  }

  private void buildDiceView() {
    List<Die> totalDice = dice.getDice();

    double numberOfDice = totalDice.size();
    double diceWidth = ((width / numberOfDice) / 2);
    DieView dieView;
    if (diceWidth > 50) {
      diceWidth = 50;
    }

    for (Die die : totalDice) {
      dieView = new DieView(die, diceWidth);
      dieViews.add(dieView);
      diceView.getChildren().add(dieView);
    }
    diceView.setOnMouseClicked(event -> controller.takeTurn());
    diceView.setAlignment(Pos.CENTER);
  }

}
