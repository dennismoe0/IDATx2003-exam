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
  }

  @Test
  void moveCurrentPlayer() {
  }
}