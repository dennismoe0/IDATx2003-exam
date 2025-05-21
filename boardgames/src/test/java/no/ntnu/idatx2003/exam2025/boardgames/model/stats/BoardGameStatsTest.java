package no.ntnu.idatx2003.exam2025.boardgames.model.stats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.List;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.LudoStats;
import no.ntnu.idatx2003.exam2025.boardgames.model.stats.boardgames.SnakesAndLaddersStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Combined tests for LudoStats and SnakesAndLaddersStats implementations of
 * BoardGameStats.
 */
class BoardGameStatsTest {
  private LudoStats ludo;
  private SnakesAndLaddersStats snakes;

  @BeforeEach
  void setUp() {
    ludo = new LudoStats();
    snakes = new SnakesAndLaddersStats();
  }

  @Test
  void testLudoDefaultStats() {
    // names order
    List<String> names = List.of(
        "wins", "losses", "gamesPlayed", "piecesCompleted",
        "doubleSixRolls", "piecesKnocked", "totalMoves", "totalDiceRolls");
    assertIterableEquals(names, ludo.getStatNames());
    // all zero by default
    List<Integer> values = List.of(0, 0, 0, 0, 0, 0, 0, 0);
    assertIterableEquals(values, ludo.getStatValues());
  }

  @Test
  void testSnakesDefaultStats() {
    List<String> names = List.of(
        "wins", "losses", "gamesPlayed", "laddersUsed",
        "snakesUsed", "highestDiceRoll", "totalDiceRolls", "totalMoves");
    assertIterableEquals(names, snakes.getStatNames());
    List<Integer> values = List.of(0, 0, 0, 0, 0, 0, 0, 0);
    assertIterableEquals(values, snakes.getStatValues());
  }

  @Test
  void testLudoIncrements() {
    ludo.incrementWins();
    ludo.incrementLosses();
    ludo.incrementGamesPlayed();
    ludo.incrementPiecesCompleted();
    ludo.incrementDoubleSixRolls();
    ludo.incrementPiecesKnocked();
    ludo.incrementMove(5);
    ludo.incrementDiceRoll();

    // wins=1, losses=1, gamesPlayed incremented twice via wins/losses and 1 more =>
    // 3
    assertEquals(1, ludo.getWins());
    assertEquals(1, ludo.getLosses());
    assertEquals(3, ludo.getGamesPlayed());
    assertEquals(1, ludo.getPiecesCompleted());
    assertEquals(1, ludo.getDoubleSixRolls());
    assertEquals(1, ludo.getPiecesKnocked());
    assertEquals(5, ludo.getTotalMoveCount());
    assertEquals(1, ludo.getTotalDiceRolls());
  }

  @Test
  void testSnakesIncrements() {
    snakes.incrementWins();
    snakes.incrementLosses();
    snakes.incrementGamesPlayed();
    snakes.incrementLaddersUsed();
    snakes.incrementSnakesUsed();
    snakes.setHighestDiceRoll(6);
    snakes.incrementMove(3);
    snakes.incrementDiceRoll();

    assertEquals(1, snakes.getWins());
    assertEquals(1, snakes.getLosses());
    assertEquals(3, snakes.getGamesPlayed());
    assertEquals(1, snakes.getLaddersUsed());
    assertEquals(1, snakes.getSnakesUsed());
    assertEquals(6, snakes.getHighestDiceRoll());
    assertEquals(3, snakes.getTotalMoveCount());
    assertEquals(1, snakes.getTotalDiceRolls());
  }
}