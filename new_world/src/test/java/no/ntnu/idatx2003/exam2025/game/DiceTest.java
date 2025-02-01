package no.ntnu.idatx2003.exam2025.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class DiceTest {

  Dice die = new Dice();

  @Test
  void rollDice() {
    int[] ints = new int[8];
    for (int i = 0; i < 500; i++) {
      int n = die.RollDice();
      ints[n]++;
    }
    assertEquals(0, ints[0]);
    assertEquals(0, ints[7]);
  }
}