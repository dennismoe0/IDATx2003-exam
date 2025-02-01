package no.ntnu.idatx2003.exam2025.game;

import java.util.Random;

public class Dice {
  private int value;
  private int faces;
  private Random rng;

  public Dice() {
    this.value = 1;
    this.faces = 6;
    this.rng = new Random();
  }

  public int getFaces() {
    return faces;
  }
  public void setFaces(int faces) {
    this.faces = faces;
  }
  public int getValue() {
    return value;
  }
  public void setValue(int value) {
    this.value = value;
  }

  /**
   * The Roll function generates a random number based on the number
   * of faces on the dice.
   * @return Returns an integer.
   */
  public int Roll(){
    return rng.nextInt(faces) + 1;
  }
}
