package no.ntnu.idatx2003.exam2025.boardgames.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.BoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.service.GameEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameSessionTest {
  private GameSession session;

  @BeforeEach
  void setUp() {
    session = new GameSession();
  }

  @Test
  void testAddAndRemovePlayer() {
    Player p1 = new Player(1, "Alice", 20);
    Player p2 = new Player(2, "Bob", 25);

    session.addPlayer(p1);
    session.addPlayer(p2);
    List<Player> players = session.getPlayers();
    assertEquals(2, players.size());
    assertTrue(players.contains(p1));
    assertTrue(players.contains(p2));

    session.removePlayer(p1);
    players = session.getPlayers();
    assertEquals(1, players.size());
    assertFalse(players.contains(p1));
    assertTrue(players.contains(p2));
  }

  @Test
  void testGameOverHandlerInvoked() {
    // Create a stub BoardGame with a controllable gameOver property
    class StubGame extends BoardGame {
      private final BooleanProperty over = new SimpleBooleanProperty(false);
      private Player winner;

      @Override
      public BooleanProperty getGameOverProperty() {
        return over;
      }

      @Override
      public String getName() {
        return "StubGame";
      }

      @Override
      public Player getWinner() {
        return winner;
      }

      public void setWinner(Player p) {
        this.winner = p;
      }
    }

    StubGame game = new StubGame();
    Player winner = new Player(5, "Carol", 30);
    game.setWinner(winner);

    // Track invocation
    final boolean[] called = { false };
    GameEventHandler handler = g -> {
      called[0] = true;
      assertEquals(game, g);
    };

    session.setGameEventHandler(handler);
    session.setBoardGame(game);

    // Before triggering, should not be called
    assertFalse(called[0]);

    // Trigger game over
    game.getGameOverProperty().set(true);

    assertTrue(called[0], "GameEventHandler.handleGameOver should be invoked on game over");
  }
}