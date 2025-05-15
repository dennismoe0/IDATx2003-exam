package no.ntnu.idatx2003.exam2025.boardgames.model.stats;

import java.util.List;

/**
 * Represents statistics for a player in a board game.
 */
public interface PlayerStats {

  /**
   * Gets the names of the statistics tracked for the player.
   *
   * @return a list of statistic names
   */
  List<String> getStatNames();

  /**
   * Gets the values of the statistics tracked for the player.
   *
   * @return a list of statistic values
   */
  List<Integer> getStatValues();
}
