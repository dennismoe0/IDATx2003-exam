package no.ntnu.idatx2003.exam2025.boardgames.model.tile.tilefactory;


import com.google.gson.JsonObject;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;

/**
 * Default tile factory interface for use with creating new Tile Factories.
 */
public interface TileFactory {
  /**
   * Default method for Tile Factories. Returns a Tile parsed from a Json object.
   *
   * @param json the JSOn code to be used.
   * @return Returns a Tile object.
   */
  Tile tileFromJson(JsonObject json, Board board);
}
