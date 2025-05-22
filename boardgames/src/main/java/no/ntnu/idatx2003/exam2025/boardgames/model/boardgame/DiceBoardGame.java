package no.ntnu.idatx2003.exam2025.boardgames.model.boardgame;

import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;
import no.ntnu.idatx2003.exam2025.boardgames.model.Die;

/**
 * A class representing Dice Based board games.
 */
public abstract class DiceBoardGame extends BoardGame {
  private Dice dice;

  public void setDice(Dice dice) {
    this.dice = dice;
  }

  public Dice getDice() {
    return dice;
  }
}
