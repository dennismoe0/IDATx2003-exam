package no.ntnu.idatx2003.exam2025.boardgames.service;

import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.BoardGame;

/**
 * Interface for handling the end of a board game.
 * This could definitely also be an extension of a GameEventHandler, but we dont
 * have the time.
 */
public interface GameEndHandler {

  /**
   * Handles the end of the game for the given BoardGame.
   *
   * @param game the BoardGame instance that has ended
   */
  void handleGameOver(BoardGame game);
}
