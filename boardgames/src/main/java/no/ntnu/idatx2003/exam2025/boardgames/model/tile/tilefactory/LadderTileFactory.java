package no.ntnu.idatx2003.exam2025.boardgames.model.tile.tilefactory;

import com.google.gson.JsonObject;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.LadderTileStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;

/**
 * Factory for building a Ladder Tile.
 */
public class LadderTileFactory implements TileFactory {

  @Override
  public Tile tileFromJson(JsonObject json, Board board) {
    int id = json.get("id").getAsInt();
    int exitId = json.get("exitId").getAsInt();

    LadderTileStrategy strategy = new LadderTileStrategy(board.getTile(id), board.getTile(exitId));

    return new Tile(id, strategy);
  }
}
