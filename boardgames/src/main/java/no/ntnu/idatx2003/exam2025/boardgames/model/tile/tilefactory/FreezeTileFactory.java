package no.ntnu.idatx2003.exam2025.boardgames.model.tile.tilefactory;

import com.google.gson.JsonObject;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.FreezeTileStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;

public class FreezeTileFactory implements TileFactory {

  /**
   * Default method for Tile Factories. Returns a Tile parsed from a Json object.
   *
   * @param json  the JSOn code to be used.
   * @param board the board that the tile will be added to.
   * @return Returns a Tile object.
   */
  @Override
  public Tile tileFromJson(JsonObject json, Board board) {
    int id = json.get("id").getAsInt();
    int duration = json.get("duration").getAsInt();
    FreezeTileStrategy strategy = new FreezeTileStrategy(duration);

    return new Tile(id, strategy);
  }
}
