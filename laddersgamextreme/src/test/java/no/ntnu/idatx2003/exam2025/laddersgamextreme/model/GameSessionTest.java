package no.ntnu.idatx2003.exam2025.laddersgamextreme.model;

import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class GameSessionTest {
  BoardModel board = new BoardModel(5,12);
  GameSession gameSession = new GameSession(board);
  @Test
  void addPlayer() {
    //Create a list of playing pieces.
    //Assuming game session has no pieces yet, we're confirming that the list size is zero.
    List<PlayingPiece> playingPieces = gameSession.getPlayerPieces();
    assertEquals(0, playingPieces.size());

    //Try adding a player to the game session.
    Player Dave = new Player("Dave",200);
    gameSession.addPlayer(Dave, Color.RED);

    //Check to see if the new player has been added.
    assertEquals(1, playingPieces.size());
    assertEquals(Dave, playingPieces.get(0).getPlayer());
  }

  @Test
  void removePlayer() {
    //Add two players to the game to see if we can then remove them.
    Player Dave = new Player("Dave",200);
    Player player2 = new Player("Julian",75);
    gameSession.addPlayer(Dave, Color.RED);
    gameSession.addPlayer(player2, Color.BLUE);

    //Get list of players.
    List<PlayingPiece> playingPieces = gameSession.getPlayerPieces();
    assertEquals(2, playingPieces.size());

    //Remove a player
    gameSession.removePlayer(player2);
    assertEquals(1, playingPieces.size());
  }

  @Test
  void moveCurrentPlayer() {
    Player Dave = new Player("Dave",200);
    Player player2 = new Player("Julian",75);
    gameSession.addPlayer(Dave, Color.RED);
    gameSession.addPlayer(player2, Color.BLUE);

    //Check to see players current positions
    gameSession.moveCurrentPlayer(5);

    List<PlayingPiece> playingPieces = gameSession.getPlayerPieces();
    System.out.println("Printing Player piece positions.");
    for (PlayingPiece piece : playingPieces) {
      System.out.println(piece.getPlayer().getPlayerName() + "'s Piece, current tile: " + piece.getCurrentTile());
    }
    assertEquals(5,playingPieces.getFirst().getCurrentTile());
    gameSession.moveCurrentPlayer(10);
    for (PlayingPiece piece : playingPieces) {
      System.out.println(piece.getPlayer().getPlayerName() + "'s Piece, current tile: " + piece.getCurrentTile());
    }
    assertEquals(10,playingPieces.get(1).getCurrentTile());
  }
}