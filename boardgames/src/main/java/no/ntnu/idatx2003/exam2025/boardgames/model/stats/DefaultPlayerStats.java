package no.ntnu.idatx2003.exam2025.boardgames.model.stats;

import no.ntnu.idatx2003.exam2025.boardgames.model.Dice;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

/**
 * Potentially a placeholder for tracking moves etc atm.
 * Would need a DAO class to save these in the database. Peristence.
 */
public class DefaultPlayerStats implements PlayerStats {

  // Sets the initial value
  private int moveCount = 0;
  private static final Logger log = Log.get(Dice.class);

  @Override
  public void incrementMove() {
    moveCount++;

    // Using this here might be scary idk, it refers to the object this is passed
    // on.
    // which should be a player
    log.debug("Incremented total movecount for player: {}", this);
  }

  @Override
  public int getTotalMoveCount() {
    return moveCount;
  }
}
