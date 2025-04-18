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
    //adding tiles to the board
    for (int i = 0; i < 12; i++) {
      board.setTile(i,new Tile(i, new EmptyTileStrategy()));
    }
    //need to manually set the next tile for the time being, since we don't have a factory set up quite yet.
    for(int i = 0; i < 12; i++){
      if(i+1 < 12){
        board.getTile(i).setNextTile(board.getTile(i+1));
      }
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
    gamePiece.move(6);
    assertEquals(gamePiece.getCurrentTile(),board.getTile(6));
  }
}