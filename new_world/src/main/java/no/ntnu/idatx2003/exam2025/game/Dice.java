package no.ntnu.idatx2003.exam2025.game;

import java.util.Random;

public class Dice {
  private int value;
  private int faces;
  private final Random rng;

  public Dice(){
    this.value = 1;
    this.faces = 6;
    this.rng = new Random();
  }

  public Dice(int faces) {
    this.value = 1;
    this.faces = faces;
    this.rng = new Random();
  }

  public int getValue() {
    return value;
  }

  public int getFaces() {
    return faces;
  }

  public void setFaces(int input){
    faces = input;
  }

  public void setValue(int input){
    value = input;
  }

  /**
   * The Roll Dice method generates a random integer within the bounds of the dice size.
   * @return Returns an Int greater than 0.
   */
  public int RollDice(){
    return rng.nextInt(faces) + 1;
  }

}
