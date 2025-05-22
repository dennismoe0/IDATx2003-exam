package no.ntnu.idatx2003.exam2025.boardgames.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javafx.scene.paint.Color;
import no.ntnu.idatx2003.exam2025.boardgames.model.gamepiece.BasicMovementStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.gamepiece.DoubleMovementStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.gamepiece.FreezeMovementStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.gamepiece.MovementStrategy;
import no.ntnu.idatx2003.exam2025.boardgames.model.tile.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// tests for GamePiece and movement strategies
class GamePieceTest {
  private Player player;
  private Tile tile1;
  private Tile tile2;
  private Tile tile3;

  @BeforeEach
  void setUp() {
    player = new Player(1, "TestPlayer", 20);
    player.setPlayerColor(Color.BLUE);

    tile1 = new Tile(1);
    tile2 = new Tile(2);
    tile3 = new Tile(3);
    tile1.setNextTile(tile2);
    tile2.setNextTile(tile3);

    new GamePiece(tile1, player).resetIdCounter();
  }

  @Test
  void testMoveAdvancesCorrectly() {
    GamePiece piece = new GamePiece(tile1, player);
    piece.move(2);
    // expect lands on tile2
    assertEquals(2, piece.getCurrentTile().getId());
  }

  @Test
  void testMoveStopsAtEndIfNoNextTile() {
    tile2.setNextTile(null);
    GamePiece piece = new GamePiece(tile1, player);
    piece.move(5);
    // expect stop at tile2
    assertEquals(2, piece.getCurrentTile().getId());
  }

  @Test
  void testMoveThrowsIfNoStartingTile() {
    GamePiece piece = new GamePiece(null, player);
    assertThrows(IllegalStateException.class, () -> piece.move(1));
  }

  @Test
  void testToStringContainsIds() {
    GamePiece piece = new GamePiece(tile1, player);
    piece.move(1);
    String desc = piece.toString();
    // desc has ids
    assertEquals(true, desc.contains("gamePieceId=0"));
    assertEquals(true, desc.contains("previousTile=1"));
    assertEquals(true, desc.contains("currentTile=1"));
  }

  @Test
  void testBasicMovementStrategy() {
    MovementStrategy strat = new BasicMovementStrategy();
    // basic returns same roll
    assertEquals(4, strat.computeMovement(4));
  }

  @Test
  void testDoubleMovementStrategy() {
    MovementStrategy strat = new DoubleMovementStrategy();
    GamePiece piece = new GamePiece(tile1, player);
    piece.setMovementStrategy(strat);
    // double should move twice
    assertEquals(4, strat.computeMovement(2));
    // onTurnEnd should decrement and revert after two uses
    strat.onTurnEnd(piece);
    strat.onTurnEnd(piece);
    // now strategy should be back to basic
    assertEquals(BasicMovementStrategy.class, piece.getMovementStrategy().getClass());
  }

  @Test
  void testFreezeMovementStrategy() {
    GamePiece piece = new GamePiece(tile1, player);
    MovementStrategy strat = new FreezeMovementStrategy(1);
    piece.setMovementStrategy(strat);
    // freeze always returns 0
    assertEquals(0, strat.computeMovement(5));
    strat.onTurnEnd(piece);
    // after one turn, should revert to basic
    assertEquals(BasicMovementStrategy.class, piece.getMovementStrategy().getClass());
  }
}