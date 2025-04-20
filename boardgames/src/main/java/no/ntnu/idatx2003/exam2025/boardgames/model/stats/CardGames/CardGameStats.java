package no.ntnu.idatx2003.exam2025.boardgames.model.stats.CardGames;

import no.ntnu.idatx2003.exam2025.boardgames.model.stats.PlayerStats;

/**
 * Represents statistics for a card game, extending player-specific statistics.
 */
public interface CardGameStats extends PlayerStats {

  /**
   * Tracks the action of drawing a card in the game.
   */
  void cardsDrawn();

  /**
   * Tracks the action of playing a card in the game.
   */
  void cardsPlayed();

  /**
   * Retrieves the total number of cards drawn by the player.
   *
   * @return the number of cards drawn
   */
  int getCardsDrawn();

  /**
   * Retrieves the total number of cards played by the player.
   *
   * @return the number of cards played
   */
  int getCardsPlayed();
}
