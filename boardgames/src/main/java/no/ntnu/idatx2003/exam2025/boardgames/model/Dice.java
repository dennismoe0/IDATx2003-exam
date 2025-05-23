package no.ntnu.idatx2003.exam2025.boardgames.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

/**
 * Represents a list of Die from the Die class.
 * Gives output based on rolling of multiple dice.
 */
public class Dice {

  private static final Logger log = Log.get(Dice.class);
  private final List<Die> dice;
  private int lastRoll = 0;

  /**
   * Constructs a Dice object with a list of Die.
   *
   * @param dice a list of Die objects (non-null/empty)
   * @throws IllegalArgumentException if the list is null/empty
   */
  public Dice(List<Die> dice) {
    if (dice == null || dice.isEmpty()) {
      log.error("Tried to create Dice with null or empty list.");
      throw new IllegalArgumentException("Need at least 1 die in the list.");
    }
    this.dice = dice;
    log.debug("Successfully created Dice with {} dice", dice.size());
  }

  /**
   * Alternate constructor takes zero argument to create an empty Dice class.
   */
  public Dice() {
    this.dice = new ArrayList<>();
  }

  /**
   * Calls the roll method for each individual die.
   * Did this instead of pretending to roll to be more Object Oriented.
   *
   * @return A list of each individual dice roll.
   */
  public List<Integer> rollAllDice() {
    List<Integer> results = dice.stream()
        .map(Die::roll).collect(Collectors.toList());

    log.info("Rolled all dice: {}", results);
    return results;
  }

  /**
   * Uses the rollAllDice method to get a sum-value of the rolled dice.
   *
   * @return A whole number representing the combined value of rolls.
   */
  public int rollAllDiceSum() {
    int sum = rollAllDice().stream().mapToInt(Integer::intValue).sum();
    log.info("Sum of all dice rolls: {}", sum);
    lastRoll = sum;
    return sum;
  }

  public int getLastRoll() {
    return lastRoll;
  }

  /**
   * Adds a dice to the list of dice.
   *
   * @param die A die object which is used to roll integers.
   */
  public void addDice(Die die) {
    dice.add(die);
  }

  public List<Die> getDice() {
    return dice;
  }
}
