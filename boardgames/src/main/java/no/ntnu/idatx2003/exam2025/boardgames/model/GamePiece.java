package no.ntnu.idatx2003.exam2025.boardgames.model;

import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

/**
 * Basic piece that can move around the game board.
 */
public class GamePiece {
  private static final Logger log = Log.get(GamePiece.class);
  private Tile previousTile;
  private Tile currentTile;

  public GamePiece() {
    currentTile = null;
    log.debug("GamePiece created");
  }

  public Tile getCurrentTile() {
    return currentTile;
  }

  public void setCurrentTile(Tile tile) {
    currentTile = tile;
  }

  /**
   * A function for moving the game piece.
   * 
   * @param steps An integer getting the number of spaces the piece will move.
   */
  public void move(int steps) {
    this.previousTile = currentTile;
    for (int i = 0; i < steps; i++) {
      if (currentTile.getNextTile() != null) {
        currentTile = currentTile.getNextTile();
      } else {
        break;
      }
    }
    currentTile.getTileStrategy().applyEffect(this);
    log.debug("Moved {} steps, from tile({}) to tile({})", steps, previousTile.getId(), currentTile.getId());
  }
}
