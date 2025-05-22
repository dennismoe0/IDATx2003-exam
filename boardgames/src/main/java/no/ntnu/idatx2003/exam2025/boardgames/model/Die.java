package no.ntnu.idatx2003.exam2025.boardgames.model;

import java.util.Random;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

/**
 * A class representing a single Die object, for use in board games.
 */
public class Die {
  private static final Logger log = Log.get(Die.class);
  private static final Random RNG = new Random();
  private final int sides;
  private final IntegerProperty roll = new SimpleIntegerProperty();


  /**
   * A method for retrieving and binding an integer property in Javafx.
   *
   * @return returns an Integer Property.
   */
  public IntegerProperty rollProperty() {
    return roll;
  }

  public int getRoll() {
    return roll.get();
  }

  /**
   * Manually set the result of a die.
   *
   * @param roll an int representing the result you want to set.
   */
  public void setRoll(int roll) {
    this.roll.set(roll);
  }

  /**
   * Die constructor that takes in the amount of sides the die should have.
   * Has logging for too few sides and creation.
   *
   * @param sides Amount of sides wanted on the die.
   */
  public Die(int sides) {
    if (sides < 2) {
      log.error("Invalid number of sides for die: {}", sides);
      throw new IllegalArgumentException("A die needs at least 2 sides...");
    }
    this.sides = sides;
    log.debug("Created die with {} sides", sides);
  }

  public int getSides() {
    return sides;
  }

  /**
   * Basic roll method using amount of sides to give a pseudorandom value. +1
   * since it starts at 0.
   *
   * @return The "face" value that was rolled.
   */
  public int roll() {
    int result = RNG.nextInt(sides) + 1;
    log.info("Rolled a {}", result);
    roll.set(result);
    return result;
  }
}
