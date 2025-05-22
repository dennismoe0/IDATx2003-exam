package no.ntnu.idatx2003.exam2025.boardgames.util;

import no.ntnu.idatx2003.exam2025.boardgames.model.Player;

/**
 * Used for tracking changes in Game State.
 */
public class LadderGameMessage {
  private final Player player;
  private final int startTile;
  private final int endTile;
  private final int roll;

  /**
   * The default message constructor.
   *
   * @param player    the player to be included in the message.
   * @param startTile the int id of the tile the player piece started on.
   * @param endTile   the int id of the tile the player piece ended on.
   * @param roll      the value of the roll the player rolled.
   */
  public LadderGameMessage(Player player, int startTile, int endTile, int roll) {
    this.player = player;
    this.startTile = startTile;
    this.endTile = endTile;
    this.roll = roll;
  }

  public Player getPlayer() {
    return player;
  }

  public int getStartTile() {
    return startTile;
  }

  public int getEndTile() {
    return endTile;
  }

  public int getRoll() {
    return roll;
  }
}
