package no.ntnu.idatx2003.exam2025.laddersgamextreme.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {
  private Dice dice;
  @BeforeEach
  void setUp() {
    dice = new Dice(6);
  }
  @Test
  void roll() {
    // Roll 1000 times to ensure the roll is always within the specified range, and never exceeds it.
    int iterations = 1000;
    for (int i = 0; i < iterations; i++) {
      assertTrue(dice.roll() < 7);
      assertTrue(dice.roll() > 0);
    }
  }
}