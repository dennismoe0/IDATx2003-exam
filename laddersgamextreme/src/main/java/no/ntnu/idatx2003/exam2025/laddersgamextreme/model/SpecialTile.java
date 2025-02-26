package no.ntnu.idatx2003.exam2025.laddersgamextreme.model;

/**
 * Represents a special tile in the game which can have various effects on the
 * playing piece.
 * All special tile behaviours are stored here but managed by the manager.
 *
 * @author Dennis Moe
 */
public class SpecialTile {

  /**
   * Enum representing the different types of special tiles in the game.
   */
  public enum TileType {
    LADDER,
    SNAKE,
    FINISH,
    START_TILE,
    PLAYER_RESET,
    SKIP_TURN,
    RESET_TURN
  }

  private final int entryTile;
  private final int exitTile;
  private final TileType tileType;

  /**
   * Constructs a SpecialTile with the specified entry tile, exit tile, and tile
   * type.
   *
   * @param entryTile the tile where the special effect "starts"
   * @param exitTile  the tile where the special effect "ends"
   * @param tileType  the type of the special tile
   */
  public SpecialTile(int entryTile, int exitTile, TileType tileType) {
    this.entryTile = entryTile;
    this.exitTile = exitTile;
    this.tileType = tileType;
  }

  public int getEntryTile() {
    return entryTile;
  }

  public int getExitTile() {
    return exitTile;
  }

  public TileType getTileType() {
    return tileType;
  }

  /**
   * Applies the standard ladders & snake movement effect.
   * I.e. goes from the start of the ladder to the exit.
   *
   * @param playingPiece the playing piece to apply the effect to
   */
  public void applyMoveEffect(PlayingPiece playingPiece) {
    playingPiece.setCurrentTile(exitTile);
  }
}
