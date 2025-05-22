package no.ntnu.idatx2003.exam2025.boardgames.model.board;

import com.google.gson.JsonObject;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.LadderTileStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.util.GsonFileReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class BoardFactoryTest {

  /**
   * Test checks for correct tile placement and board size (number of tiles).
   */
  @Test
  void createDefaultLadderBoard() {
    BoardFactory boardFactory = new BoardFactory();
    Board ladderGameBoard = boardFactory.createDefaultLadderBoard();
    assertNotNull(ladderGameBoard);
    //Board follows a set pattern. Since we know what tile 1 should be
    //We can check directly for the strategy it should have.
    assertEquals(LadderTileStrategy.class, ladderGameBoard.getTile(1).getTileStrategy().getClass());
    //Now we check to make sure they're not all ladder tiles.
    assertNotEquals(LadderTileStrategy.class, ladderGameBoard.getTile(2).getTileStrategy().getClass());
    //Now we check to make sure we have the correct number of tiles.
    assertEquals(90, ladderGameBoard.getBoardSize());
  }

  @Test
  void buildBoardFromJson(){
    BoardFactory boardFactory = new BoardFactory();
    GsonFileReader gsonFileReader = new GsonFileReader();
    Board board = null;
    try{
      JsonObject json = gsonFileReader.readJson(
          "src/main/resources/assets/boards/laddergameboards/laddergame_classic.json");
      board = boardFactory.buildBoardFromJson(json);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    assertNotNull(board);
    assertEquals(90, board.getBoardSize());
    assertEquals(LadderTileStrategy.class, board.getTile(1).getTileStrategy().getClass());
  }
}