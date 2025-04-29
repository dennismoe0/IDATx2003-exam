package no.ntnu.idatx2003.exam2025.boardgames.model.board;

import no.ntnu.idatx2003.exam2025.boardgames.model.tile.LadderTileStrategy;
import org.junit.jupiter.api.Test;

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
}