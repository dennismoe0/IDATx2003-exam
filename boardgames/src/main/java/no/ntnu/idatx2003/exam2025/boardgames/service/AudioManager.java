package no.ntnu.idatx2003.exam2025.boardgames.service;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

/**
 * Manages playback of in-game sound effects.
 */
public class AudioManager {
  private static final String SOUND_PATH = "/assets/sound/";
  private static final Logger log = Log.get(AudioManager.class);
  private int diceSoundIndex = 0;
  private static final int diceVariants = 3;

  /**
   * Plays the victory sound effect.
   */
  public void playVictorySound() {
    String fileName = "victory.wav";
    playSound(fileName);
    log.info("Playing sound {}", fileName);
  }

  /**
   * Plays the dice roll sound effect.
   */
  public void playDiceRollSound() {
    diceSoundIndex = (diceSoundIndex % diceVariants) + 1;
    String fileName = "dice_roll" + diceSoundIndex + ".wav";
    playSound(fileName);
    log.info("Playing sound {}", fileName);
  }

  /**
   * Plays the piece-moving sound effect.
   */
  public void playMoveSound() {
    String fileName = "move_piece.wav";
    playSound(fileName);
    log.info("Playing sound {}", fileName);
  }

  /**
   * Internally loads and plays a sound file from the assets directory.
   *
   * @param fileName the name of the sound file within /assets/sound/
   */
  private void playSound(String fileName) {
    String resource = SOUND_PATH + fileName;
    try (AudioInputStream audioIn = getAudioStream(resource)) {
      Clip clip = AudioSystem.getClip();
      clip.open(audioIn);
      clip.start();
    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
      System.err.println("Failed to play sound: " + resource + " (" + e.getMessage() + ")");
    }
  }

  /**
   * Retrieves an AudioInputStream for the given classpath resource.
   *
   * @param resourcePath path to resource within classpath
   * @return AudioInputStream for playback
   * @throws IOException                   if the sound resource cannot be found
   *                                       or read
   * @throws UnsupportedAudioFileException if the audio file format is not
   *                                       supported
   */
  private AudioInputStream getAudioStream(String resourcePath)
      throws IOException, UnsupportedAudioFileException {
    URL soundUrl = getClass().getResource(resourcePath);
    if (soundUrl == null) {
      throw new IOException("Sound resource not found: " + resourcePath);
    }
    return AudioSystem.getAudioInputStream(soundUrl);
  }
}
