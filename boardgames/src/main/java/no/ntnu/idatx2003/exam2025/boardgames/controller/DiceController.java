package no.ntnu.idatx2003.exam2025.boardgames.controller;

import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;

/**
 * Controller for communicating between dice and View.
 */
public class DiceController {
  private Dice dice;

  /**
   * The default Dice Controller constructor.
   *
   * @param dice the dice from the selected Board Game.
   */
  public DiceController(Dice dice) {
    this.dice = dice;
  }

  /**
   * A method for rolling the dice. Returns void.
   */
  public void rollDice() {
    dice.rollAllDiceSum();
  }
}
