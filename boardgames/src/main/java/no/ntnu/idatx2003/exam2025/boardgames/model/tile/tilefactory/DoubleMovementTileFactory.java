package no.ntnu.idatx2003.exam2025.boardgames.model.tile.tilefactory;

import com.google.gson.JsonObject;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.DoubleMovementTileStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;

/**
 * A factory for building DoubleMovement tiles.
 */
public class DoubleMovementTileFactory implements TileFactory {

  /**
   * Default method for Tile Factories. Returns a Tile parsed from a Json object.
   *
   * @param json  the JSOn code to be used.
   * @param board the board object to be referenced.
   * @return Returns a Tile object.
   */

  @Override
  public Tile tileFromJson(JsonObject json, Board board) {
    int id = json.get("id").getAsInt();
    return new Tile(id, new DoubleMovementTileStrategy());
  }
}
