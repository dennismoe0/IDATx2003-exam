package no.ntnu.idatx2003.exam2025.boardgames.model;

import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

/**
 * Basic piece that can move around the game board.
 * Would need to be refactored if we want pieces to be special (Chess)
 */
public class GamePiece {

  private static final Logger log = Log.get(GamePiece.class);

  private static int idCounter = 0;

  private final int gamePieceId;
  private Tile previousTile;
  private Tile currentTile;
  private Tile startingTile;

  /**
   * Constructs a new GamePiece with the specified starting tile.
   * Non-persisting id to make unique session-based pieces.
   *
   * @param startingTile The tile where the game piece starts.
   */
  public GamePiece(Tile startingTile) {
    this.startingTile = startingTile;
    this.gamePieceId = idCounter++;
    currentTile = null;
    log.debug("GamePiece {} created", gamePieceId);
  }

  /**
   * Resets the ID counter for game pieces.
   * This is useful for restarting the game session.
   * Use before changing games potentially.
   */
  public void resetIdCounter() {
    idCounter = 0;
  }

  public int getGamePieceId() {
    return gamePieceId;
  }

  public Tile getCurrentTile() {
    return currentTile;
  }

  /**
   * Updates the current tile of the game piece.
   *
   * @param tile The new tile to set as the current position of the game piece.
   */
  public void setCurrentTile(Tile tile) {
    currentTile = tile;
    log.debug("Set position of Game Piece {} to tile {}", gamePieceId, currentTile);
  }

  /**
   * Sets the starting tile of the game piece.
   *
   * @param tile The tile to set as the starting position of the game piece.
   */
  public void setStartingTile(Tile tile) {
    startingTile = tile;
    log.debug("Set starting tile of {} to {}.", gamePieceId, startingTile);
  }

  public Tile getStartingTile() {
    return startingTile;
  }

  /**
   * A function for moving the game piece.
   *
   * @param steps An integer getting the number of spaces the piece will move.
   */
  public void move(int steps) {

    if (currentTile == null) {
      if (startingTile != null) {
        currentTile = startingTile;
      }
    }
    if (currentTile.getNextTile() == null) {
      throw new IllegalArgumentException("Cannot move to a non-existent tile.");
    }

    this.previousTile = currentTile;
    for (int i = 0; i < steps; i++) {
      if (currentTile.getNextTile() != null) {
        currentTile = currentTile.getNextTile();
      } else {
        break;
      }
    }
    if (currentTile.getTileStrategy() != null) {
      currentTile.getTileStrategy().applyEffect(this);
    }

    log.debug("GamePiece {} moved {} steps, from tile({}) to tile({})", gamePieceId, steps,
        previousTile.getId(), currentTile.getId());
  }

  @Override
  public String toString() {
    return "GamePiece{"
        +
        "gamePieceId=" + gamePieceId
        +
        ", previousTile=" + (previousTile != null ? previousTile.getId() : "null")
        +
        ", currentTile=" + (currentTile != null ? currentTile.getId() : "null")
        +
        ", startingTile=" + (startingTile != null ? startingTile.getId() : "null")
        +
        '}';
  }
}
