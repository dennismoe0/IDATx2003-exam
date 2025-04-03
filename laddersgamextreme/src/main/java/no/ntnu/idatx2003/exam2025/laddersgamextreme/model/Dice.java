package no.ntnu.idatx2003.exam2025.laddersgamextreme.model;

import java.util.Random;

public class Dice {
  private int diceSize;
  private Random rand = new Random();
  private int lastRoll;

  public Dice(int diceSize) {
    this.diceSize = diceSize;
  }

  public Dice() {
    this.diceSize = 6;
  }

  public int roll() {
    this.lastRoll = rand.nextInt(diceSize) + 1;
    return this.lastRoll;
  }

  public void setDiceSize(int diceSize) {
    this.diceSize = diceSize;
  }

  public int getDiceSize() {
    return this.diceSize;
  }

  public int getLastRoll() {
    return this.lastRoll;
  }

}
