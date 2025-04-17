package no.ntnu.idatx2003.exam2025.laddersgamextreme.model;

import no.ntnu.idatx2003.exam2025.boardgames.model.Die;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DieTest {

  @Test
  void testDieCreationWithValidSides() {
    // ARRANGE
    int sides = 6;

    // ACT
    Die die = new Die(sides);

    // ASSERT
    assertEquals(sides, die.getSides(), "Die should have the correct number of sides");
  }

  @Test
  void testDieCreationWithInvalidSides() {
    // ARRANGE
    int sides = 1;

    // ACT & ASSERT
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class, () -> new Die(sides));
    assertEquals("A die needs at least 2 sides...", exception.getMessage(),
        "Exception message should match");
  }

  @Test
  void testRollWithinRange() {
    // ARRANGE
    int sides = 6;
    Die die = new Die(sides);

    // ACT & ASSERT, for loop to double check for false positives
    for (int i = 0; i < 100; i++) {
      int rollResult = die.roll();
      assertTrue(rollResult >= 1 && rollResult <= sides,
          "Roll result should be within the valid range");
    }
  }
}
