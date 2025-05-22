package no.ntnu.idatx2003.exam2025.boardgames.model.boardgame;

import no.ntnu.idatx2003.exam2025.boardgames.model.GamePiece;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.BoardFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LadderBoardGameTest {

  LadderBoardGame ladderBoardGame;
  Board board;
  List<Player> players = new ArrayList<>();
  Player sasha = new Player(1,"Sasha",27);
  Player dennis = new Player(2,"Dennis",24);

  @BeforeEach
  void setUpTests(){
    players.add(sasha);
    players.add(dennis);
    board = new BoardFactory().createDefaultLadderBoard();
    try{
      ladderBoardGame = new LadderBoardGame(2, board, players);
    } catch (Exception e){
      e.printStackTrace();
    }

  }

  @Test
  void setUp() {
    ladderBoardGame.setUp(players);
    GamePiece piece = ladderBoardGame.getPlayerPieces(sasha).getFirst();
    assertNotNull(piece);
    assertEquals(90,board.getBoardSize());
  }

  @Test
  void takeTurn() {
    ladderBoardGame.setUp(players);
    ladderBoardGame.takeTurn();

    assertNotEquals(1, ladderBoardGame.getFirstPlayerPiece(
        ladderBoardGame.getCurrentPlayer()).getCurrentTile().getId());
  }
}