package no.ntnu.idatx2003.exam2025.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class DiceTest {

  private Dice dice = new Dice();

  @Test
  void roll() {
    int[] ints = new int[8];
    int n;
    for (int i = 0; i < 500; i++) {
      n = dice.Roll();
      ints[n]++;
    }
    assertEquals(0, ints[0]);
    assertEquals(0, ints[7]);
  }
}