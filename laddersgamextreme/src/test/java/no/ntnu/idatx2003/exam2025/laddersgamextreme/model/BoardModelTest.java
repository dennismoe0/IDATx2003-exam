package no.ntnu.idatx2003.exam2025.laddersgamextreme.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class BoardModelTest {

  private BoardModel model = new BoardModel(9,10);
  private BoardModel offModel = new BoardModel(5,8);
  @Test
  void convertToTileNumber() {

  }

  @Test
  void convertToTileNumberZigzag() {
    // Tile on row 2, column 4 should be 25.
    // Standard 9x10 board.
    assertEquals(25,model.convertToTileNumberZigzag(2,4,10));
    assertNotEquals(66, model.convertToTileNumberZigzag(6,6,10));
    assertEquals(53, model.convertToTileNumberZigzag(5,7,10));

    //Testing non-standard 5x12 board
    //assertEquals(16, model.convertToTileNumberZigzag(2,1,8));
  }

  @Test
  void convertToRowCol() {
  }

  @Test
  void convertToRowColZigzag() {
  }
}