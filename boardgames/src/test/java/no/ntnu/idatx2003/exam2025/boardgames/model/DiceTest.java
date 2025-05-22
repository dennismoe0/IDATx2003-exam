package no.ntnu.idatx2003.exam2025.boardgames.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;

public class DiceTest {

  @Test
  void testDiceCreationWithValidSize() {

    // Arrange
    int sides = 6;

    // ACT
    Die die1 = new Die(sides);
    Die die2 = new Die(sides);

    ArrayList<Die> diceList = new ArrayList<>();
    diceList.add(die1);
    diceList.add(die2);

    Dice dicePair = new Dice(diceList);

    // Assert
    assertEquals(2, dicePair.getDice().size());
    assertTrue(dicePair.getDice().contains(die1));
    assertTrue(dicePair.getDice().contains(die2));
  }

  @Test
  void testRollIndividualValues() {

    // Arrange
    int sides = 12;

    // ACT
    Die die1 = new Die(sides);
    Die die2 = new Die(sides);

    ArrayList<Die> diceList = new ArrayList<>();
    diceList.add(die1);
    diceList.add(die2);

    Dice dicePair = new Dice(diceList);

    for (int i = 0; i < 100; i++) {
      List<Integer> rollResults = dicePair.rollAllDice();
      assertEquals(2, rollResults.size(), "The roll results should contain exactly 2 values.");
      rollResults.forEach(roll -> assertTrue(roll >= 1 && roll <= sides, "Each roll should be between 1 and " + sides));
    }
  }

  @Test
  void testRollAllSum() {
    // Arrange
    int sides = 6;

    // ACT
    Die die1 = new Die(sides);
    Die die2 = new Die(sides);

    ArrayList<Die> diceList = new ArrayList<>();
    diceList.add(die1);
    diceList.add(die2);

    Dice dicePair = new Dice(diceList);

    for (int i = 0; i < 100; i++) {
      int rollSum = dicePair.rollAllDiceSum();
      assertTrue(rollSum >= 2 && rollSum <= sides * 2);
    }
  }
}
