package no.ntnu.idatx2003.exam2025.ludo.model;

import java.util.Random;

public class Dice {
  private Random random = new Random();
  private int diceSides;
  private int lastRoll;

  public Dice(){
    diceSides = 6;
  }
  public Dice(int diceSides) {
    this.diceSides = diceSides;
  }

  public int roll(){
    this.lastRoll = random.nextInt(diceSides);
    return lastRoll;
  }

  public int getDiceSides() {
    return diceSides;
  }
  public void setDiceSides(int diceSides) {
    this.diceSides = diceSides;
  }
  public int getLastRoll() {
    return lastRoll;
  }
}
