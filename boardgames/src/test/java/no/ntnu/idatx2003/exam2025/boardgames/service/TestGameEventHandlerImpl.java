package no.ntnu.idatx2003.exam2025.boardgames.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDao;
import no.ntnu.idatx2003.exam2025.boardgames.dao.player.PlayerDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.dao.stats.boardgames.SnakesAndLaddersStatsDaoImpl;
import no.ntnu.idatx2003.exam2025.boardgames.model.Player;
import no.ntnu.idatx2003.exam2025.boardgames.model.boardgame.LadderBoardGame;
import no.ntnu.idatx2003.exam2025.boardgames.model.board.Board;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.SnakesAndLaddersStats;
import no.ntnu.idatx2003.exam2025.boardgames.util.Log;

import org.slf4j.Logger;

/**
 * Tests that GameEventHandlerImpl persists stats correctly.
 */
class TestGameEventHandlerImpl {
  private static Connection conn;
  private static PlayerDao playerDao;
  private static TestableGameEventHandlerImpl handler;

  @BeforeAll
  static void init() throws SQLException {
    // wipe and init DB
    DatabaseManager.wipeDatabase();
    conn = DatabaseManager.connect();
    DatabaseManager.initializeDatabase(conn);

    playerDao = new PlayerDaoImpl(conn);
    handler = new TestableGameEventHandlerImpl();
  }

  @AfterAll
  static void cleanup() throws SQLException {
    DatabaseManager.closeConnection(conn);
  }

  /**
   * A small Board implementation for two tiles.
   */
  private static Board makeSimpleBoard() {
    Tile t1 = new Tile(1);
    Tile t2 = new Tile(2);
    t1.setNextTile(t2);
    Map<Integer, Tile> map = new HashMap<>();
    map.put(1, t1);
    map.put(2, t2);
    return new Board() {
      @Override
      public Tile getTile(int id) {
        return map.get(id);
      }

      @Override
      public int getBoardSize() {
        return map.size();
      }
    };
  }

  @Test
  void testHandleGameOverPersistsStats() throws SQLException {
    // create two players
    Player p1 = new Player(0, "Alice", 30);
    Player p2 = new Player(0, "Bob", 25);
    int id1 = playerDao.create(p1);
    int id2 = playerDao.create(p2);
    p1.setPlayerId(id1);
    p2.setPlayerId(id2);

    // assign stats
    SnakesAndLaddersStats stats1 = new SnakesAndLaddersStats();
    stats1.incrementWins();
    stats1.incrementMove(3);
    stats1.incrementDiceRoll();
    p1.setPlayerStats(stats1);

    SnakesAndLaddersStats stats2 = new SnakesAndLaddersStats();
    stats2.incrementLosses();
    stats2.incrementMove(2);
    stats2.incrementDiceRoll();
    p2.setPlayerStats(stats2);

    // make game
    LadderBoardGame game = new LadderBoardGame(makeSimpleBoard(), List.of(p1, p2));

    // trigger
    handler.handleGameOver(game);

    // reload from DAO and assert
    SnakesAndLaddersStatsDaoImpl dao = new SnakesAndLaddersStatsDaoImpl(conn);
    SnakesAndLaddersStats loaded1 = dao.load(id1);
    SnakesAndLaddersStats loaded2 = dao.load(id2);

    assertNotNull(loaded1);
    assertNotNull(loaded2);
    // p1: 1 win, 1 dice roll, 3 moves
    assertEquals(1, loaded1.getWins());
    assertEquals(3, loaded1.getTotalMoveCount());
    assertEquals(1, loaded1.getTotalDiceRolls());

    // p2: 1 loss, 1 dice roll, 2 moves
    assertEquals(1, loaded2.getLosses());
    assertEquals(2, loaded2.getTotalMoveCount());
    assertEquals(1, loaded2.getTotalDiceRolls());
  }

  /**
   * Extends GameEventHandlerImpl to track audio calls instead of playing.
   */
  private static class TestableGameEventHandlerImpl extends GameEventHandlerImpl {
    private int playCount = 0;

    TestableGameEventHandlerImpl() throws SQLException {
      super();
    }

    protected void playVictorySound() {
      playCount++;
      Logger log = Log.get(TestableGameEventHandlerImpl.class);
      log.info("[test] pretend play victory sound");
    }

    int getPlayCount() {
      return playCount;
    }
  }
}
