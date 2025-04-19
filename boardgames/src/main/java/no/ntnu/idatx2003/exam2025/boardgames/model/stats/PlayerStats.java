package no.ntnu.idatx2003.exam2025.boardgames.model.stats;

/**
 * Interface for subimplementations that will keep track of player statistics
 * such as wins, losses, moves made, action spaces triggered etc.
 */
public interface PlayerStats {

  /**
   * Used to increment total moves made by a player with a playing piece.
   */
  void incrementMove();

  /**
   * Used to get the total moves made by a player.
   *
   * @return int count of total moves made by a player.
   */
  int getTotalMoveCount();

  // If all games allow it, then add wins, losses, etc.
  // non game-specific things
}
