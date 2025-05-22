package no.ntnu.idatx2003.exam2025.boardgames.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
  private Player newPlayer;

  @BeforeEach
  void setUp() {
    newPlayer = new Player(0, "Alice", 30);
  }

  @Test
  void testNewPlayerConstructorDefaults() {
    assertEquals(0, newPlayer.getPlayerId());
    assertEquals("Alice", newPlayer.getPlayerName());
    assertEquals(30, newPlayer.getPlayerAge());
    assertNotNull(newPlayer.getPlayerColor());
    assertNull(newPlayer.getPlayerStats());
    assertNull(newPlayer.getPlayerPieceAssetPath());
  }

  @Test
  void testSettersAndMutators() {
    newPlayer.setPlayerId(7);
    assertEquals(7, newPlayer.getPlayerId());

    newPlayer.setPlayerName("Charlie");
    assertEquals("Charlie", newPlayer.getPlayerName());

    newPlayer.setPlayerAge(55);
    assertEquals(55, newPlayer.getPlayerAge());

    newPlayer.setPlayerColor(Color.BLUE);
    assertEquals(Color.BLUE, newPlayer.getPlayerColor());

    newPlayer.setPlayerPieceAssetPath("assets/pieces/pink_piece.png");
    assertEquals("assets/pieces/pink_piece.png", newPlayer.getPlayerPieceAssetPath());
  }
}
