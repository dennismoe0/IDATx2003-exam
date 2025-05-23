package no.ntnu.idatx2003.exam2025.boardgames.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import no.ntnu.idatx2003.exam2025.boardgames.model.gamepiece.BasicMovementStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.gamepiece.MovementStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;
import org.slf4j.Logger;

/**
 * Basic piece that can move around the game board.
 * Would need to be refactored if we want pieces to be special (Chess)
 */
public class GamePiece {

  private static final Logger log = Log.get(GamePiece.class);
  private MovementStrategy movementStrategy;

  // Observer value implementation
  private final ObjectProperty<Tile> currentTile = new SimpleObjectProperty<>();

  private Color color;

  private static int idCounter = 0;

  private final int gamePieceId;
  private Tile previousTile;
  private Tile startingTile;
  private final Player player;

  /**
   * Constructs a new GamePiece with the specified starting tile.
   * Non-persisting id to make unique session-based pieces.
   *
   * @param startingTile The tile where the game piece starts.
   * @param player       connected to the piece
   */
  public GamePiece(Tile startingTile, Player player) {
    movementStrategy = new BasicMovementStrategy();
    this.startingTile = startingTile;
    this.player = player;
    this.color = player.getPlayerColor();
    this.gamePieceId = idCounter++;
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

  public Color getColor() {
    return color;
  }

  /**
   * Sets the color of the game piece.
   *
   * @param color The new color to set for the game piece.
   */
  public void setColor(Color color) {
    this.color = color;
  }

  public Player getPlayer() {
    return player;
  }

  public int getGamePieceId() {
    return gamePieceId;
  }

  public Tile getCurrentTile() {
    return currentTile.get();
  }

  /**
   * Returns the property representing the current tile of the game piece.
   *
   * @return the ObjectProperty for the current tile
   */
  public ObjectProperty<Tile> currentTileProperty() {
    return currentTile;
  }

  /**
   * Updates the current tile of the game piece.
   *
   * @param tile The new tile to set as the current position of the game piece.
   */
  public void setCurrentTile(Tile tile) {
    currentTile.set(tile);
    log.info("Set position of Game Piece {} to tile {}", gamePieceId, currentTile);
  }

  /**
   * Sets the starting tile of the game piece.
   *
   * @param tile The tile to set as the starting position of the game piece.
   */
  public void setStartingTile(Tile tile) {
    startingTile = tile;
    log.info("Set starting tile of {} to {}.", gamePieceId, startingTile);
  }

  public Tile getStartingTile() {
    return startingTile;
  }

  /**
   * Moves the game piece a specified number of steps from its current position.
   * If the current tile is null, it initializes the game piece to the starting
   * tile.
   * Throws an exception if the target tile does not exist.
   *
   * @param steps The number of steps to move the game piece.
   * @throws IllegalStateException    If the game piece has no starting tile.
   * @throws IllegalArgumentException If the target tile does not exist.
   */
  public void move(int steps) {

    int computedMove = movementStrategy.computeMovement(steps);

    if (currentTile.get() == null) {
      if (startingTile != null) {
        setCurrentTile(startingTile);
        computedMove--;
        log.info("GamePiece {} initialized to starting tile {}", gamePieceId, startingTile.getId());
      } else {
        throw new IllegalStateException("Gamepiece has no starting tile");
      }
    }

    log.info(
        "GamePiece {} attempting to move {} steps from tile {}",
        gamePieceId, computedMove, currentTile);

    Tile targetTile = currentTile.get();
    for (int i = 0; i < computedMove; i++) {
      if (targetTile.getNextTile() == null) {
        log.error("Tile {} has no next tile. Cannot move further.", targetTile.getId());
        break;
        // throw new IllegalArgumentException("Cannot move to a non-existent tile");
      }
      targetTile = targetTile.getNextTile();
      log.info(
          "GamePiece {} moved to intermediate tile {}",
          gamePieceId, targetTile.getId());
    }

    previousTile = currentTile.get();
    setCurrentTile(targetTile);

    // Apply effects
    if (currentTile.get().getTileStrategy() != null) {
      String strategyName = currentTile.get().getTileStrategy().getClass().getSimpleName();
      log.info(
          "GamePiece {} landed on tile {} with strategy. Applying effect {}.",
          gamePieceId, currentTile.get().getId(),
          strategyName);
      currentTile.get().getTileStrategy().applyEffect(this);
    }

    movementStrategy.onTurnEnd(this);

    log.info(
        "GamePiece {} finished moving. Final tile: {}",
        gamePieceId, currentTile.get().getId());
  }

  public MovementStrategy getMovementStrategy() {
    return this.movementStrategy;
  }

  public void setMovementStrategy(MovementStrategy movementStrategy) {
    this.movementStrategy = movementStrategy;
  }

  @Override
  public String toString() {
    return "GamePiece{"
        +
        "gamePieceId=" + gamePieceId
        +
        ", previousTile=" + (previousTile != null ? previousTile.getId() : "null")
        +
        ", currentTile=" + (currentTile != null ? currentTile.get().getId() : "null")
        +
        ", startingTile=" + (startingTile != null ? startingTile.getId() : "null")
        +
        '}';
  }
}
