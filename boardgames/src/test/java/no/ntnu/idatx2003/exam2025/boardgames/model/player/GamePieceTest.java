package no.ntnu.idatx2003.exam2025.boardgames.model.player;

import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.EmptyTileStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GamePieceTest {

  private static GamePiece gamePiece;
  private static Board board;


  @BeforeAll
  static void setUp(){
    gamePiece = new GamePiece();
    board = new Board();
    for (int i = 0; i < 12; i++) {
      board.setTile(i,new Tile(i, new EmptyTileStrategy()));
    }
  }
  @BeforeEach
  void setUpGamePiece(){
    gamePiece.setCurrentTile(board.getTile(0));
  }

  @Test
  void getCurrentTile() {
    assertEquals(gamePiece.getCurrentTile(),board.getTile(0));
    gamePiece.setCurrentTile(board.getTile(1));
    assertEquals(gamePiece.getCurrentTile(),board.getTile(1));
  }

  @Test
  void setCurrentTile() {
    assertEquals(gamePiece.getCurrentTile(),board.getTile(0));
    gamePiece.setCurrentTile(board.getTile(1));
    assertEquals(gamePiece.getCurrentTile(),board.getTile(1));
  }

  @Test
  void move() {
    System.out.println(gamePiece.getCurrentTile().getId());
    gamePiece.move(6);
    System.out.println(gamePiece.getCurrentTile().getId());
    assertEquals(gamePiece.getCurrentTile(),board.getTile(5));
  }
}