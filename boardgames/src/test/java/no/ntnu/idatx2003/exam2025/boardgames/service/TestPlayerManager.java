package no.ntnu.idatx2003.exam2025.boardgames.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for PlayerManager.
 */
class TestPlayerManager {
  private PlayerManager manager;
  private Player p1;
  private Player p2;

  @BeforeEach
  void setUp() {
    manager = new PlayerManager();
    p1 = new Player(1, "Alice", 25);
    p2 = new Player(2, "Bob", 30);
    // set default color so equality easy
    p1.setPlayerColor(Color.RED);
    p2.setPlayerColor(Color.BLUE);
  }

  @Test
  void testAddAndCountPlayers() {
    assertEquals(0, manager.getPlayerCount());
    manager.addPlayer(p1);
    assertEquals(1, manager.getPlayerCount());
    manager.addPlayer(p2);
    assertEquals(2, manager.getPlayerCount());
  }

  @Test
  void testRemovePlayer() {
    manager.addPlayer(p1);
    manager.addPlayer(p2);
    assertEquals(2, manager.getPlayerCount());

    manager.removePlayer(p1);
    assertEquals(1, manager.getPlayerCount());
    // remaining is p2
    ArrayList<Player> list = manager.getPlayersAsList();
    assertTrue(list.contains(p2));
    assertFalse(list.contains(p1));
  }

  @Test
  void testClearPlayers() {
    manager.addPlayer(p1);
    manager.addPlayer(p2);
    assertEquals(2, manager.getPlayerCount());

    manager.clearPlayers();
    assertEquals(0, manager.getPlayerCount());
    assertTrue(manager.getPlayersAsList().isEmpty());
  }

  @Test
  void testGetPlayersAsListOrderIndependent() {
    manager.addPlayer(p1);
    manager.addPlayer(p2);
    ArrayList<Player> list = manager.getPlayersAsList();
    // since underlying is HashSet, list order can vary but contains both
    assertTrue(list.contains(p1));
    assertTrue(list.contains(p2));
    assertEquals(2, list.size());
  }
}
