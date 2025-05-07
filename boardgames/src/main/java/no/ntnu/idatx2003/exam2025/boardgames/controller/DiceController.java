package no.ntnu.idatx2003.exam2025.boardgames.controller;

import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;

public class DiceController {
  private Dice dice;
  public DiceController(Dice dice) {
    this.dice = dice;
  }
  public void rollDice() {
    dice.rollAllDiceSum();
  }
}
