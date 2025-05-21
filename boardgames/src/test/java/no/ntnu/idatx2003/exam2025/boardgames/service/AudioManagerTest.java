package no.ntnu.idatx2003.exam2025.boardgames.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for AudioManager.
 */
public class AudioManagerTest {
  private AudioManager audioManager;
  private Field diceIndexField;

  @BeforeEach
  public void setUp() throws Exception {
    audioManager = new AudioManager();
    // Access private diceSoundIndex via reflection
    diceIndexField = AudioManager.class.getDeclaredField("diceSoundIndex");
    diceIndexField.setAccessible(true);
  }

  @Test
  public void testDiceRollCycling() throws Exception {
    // Initial index should be 0
    assertEquals(0, diceIndexField.getInt(audioManager));

    // Call playDiceRollSound() once, index => 1
    assertDoesNotThrow(() -> audioManager.playDiceRollSound());
    assertEquals(1, diceIndexField.getInt(audioManager));

    // Call second time, index => 2
    audioManager.playDiceRollSound();
    assertEquals(2, diceIndexField.getInt(audioManager));

    // Third time => 3
    audioManager.playDiceRollSound();
    assertEquals(3, diceIndexField.getInt(audioManager));

    // Fourth time wraps back to 1
    audioManager.playDiceRollSound();
    assertEquals(1, diceIndexField.getInt(audioManager));
  }

  @Test
  public void testPlayMethodsNotThrowing() {
    // Ensure that playing sounds doesn't throw, even if resources missing
    assertDoesNotThrow(() -> audioManager.playVictorySound());
    assertDoesNotThrow(() -> audioManager.playMoveSound());
    assertDoesNotThrow(() -> audioManager.playDiceRollSound());
  }
}