package no.ntnu.idatx2003.exam2025.boardgames.model;

import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

import java.util.Random;

public class Die {
  private static final Logger log = Log.get(Die.class);
  private static final Random RNG = new Random();

  private final int sides;

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
    return result;
  }
}
