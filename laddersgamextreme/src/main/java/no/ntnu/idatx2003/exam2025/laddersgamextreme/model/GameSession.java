package no.ntnu.idatx2003.exam2025.laddersgamextreme.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.scene.paint.Color;
import no.ntnu.idatx2003.exam2025.laddersgamextreme.service.SpecialTileManager;

/**
 * Manages a single session of the board game.
 * Holds BoardModel for backend definitions.
 * PlayerPieces for the tokens and handles turn order etc.
 *
 * @author Dennis Moe
 */
public class GameSession {
  private final BoardModel board;
  private List<Player> players;
  private final List<PlayingPiece> playerPieces; // Stores players in the game.
                                                 // Position is stored in the object.
  private int currentPlayerIndex; // Which player has an active turn.
  private boolean gameOver; // I.e. if a player reaches 100 it goes to true.
                            // the player at the maxTile at the end wins.
  private List<Dice> die;

  private final SpecialTileManager specialTileManager;

  /**
   * Constructs the board, a list to keep active players in and their positions +
   * game state.
   *
   * @param board The board that will be used/generated and tied to this session.
   */
  public GameSession(BoardModel board) {
    this.board = board;
    this.playerPieces = new ArrayList<>();
    this.currentPlayerIndex = 0;
    this.gameOver = false;
    this.specialTileManager = new SpecialTileManager();
  }

  /**
   * Adds a player to the list of active players for the session.
   *
   * @param player     Player to add to the game session.
   * @param pieceColor Color of the piece to identify the player.
   */
  public void addPlayer(Player player, Color pieceColor) {
    PlayingPiece piece = new PlayingPiece(player, pieceColor);
    playerPieces.add(piece);
  }

  /**
   * Removes a player from the list of active players for the session.
   *
   * @param player Player to remove from the game session.
   */
  public void removePlayer(Player player) {
    playerPieces.removeIf(piece -> piece.getPlayer().equals(player));
  }

  /**
   * Returns the list of current session players.
   *
   * @return List of players in active session.
   */
  public List<PlayingPiece> getPlayerPieces() {
    return playerPieces;
  }

  public BoardModel getBoard() {
    return board;
  }

  /**
   * Returns the maximum tile number based on the board dimensions.
   * For a standard board, this is rows * cols.
   *
   * @return the maximum tile number.
   */
  public int getMaxTile() {
    return board.getRows() * board.getCols();
  }

  public SpecialTileManager getSpecialTileManager() {
    return specialTileManager;
  }

  /**
   * Moves the current player's piece by the specified number of steps.
   * After moving, the method checks if the player has reached a special tile.
   * If so, that player wins, and the wins/losses are updated.
   *
   * @param steps the number of steps to move (can be negative). Snakes.
   */
  public void moveCurrentPlayer(int steps) {
    if (gameOver) {
      String gameOverMessage = "Game is over. No more moves allowed.";
      System.out.println(gameOverMessage);
      return;
    }

    int maxTile = getMaxTile();
    PlayingPiece currentPiece = playerPieces.get(currentPlayerIndex);
    currentPiece.move(steps, maxTile);

    // After moving check if new tile is a special one, and applies effect
    // effect is stored in
    int newTile = currentPiece.getCurrentTile();
    if (specialTileManager.isSpecialTile(newTile)) {
      SpecialTile specialTile = specialTileManager.getSpecialTileByPos(newTile);
      specialTile.applyMoveEffect(currentPiece);
    }

    // Check if the current player has reached the winning tile.
    if (currentPiece.getCurrentTile() == maxTile) {
      if (currentPiece.getPlayer() != null) {
        declareWinner(currentPiece);
      } else {
        System.out.print("Error: No player is associated to the PlayingPiece.");
      }
      gameOver = true;
      return; // Game ends immediately when a player wins.
    }

    // If no win, advance to the next player's turn.
    currentPlayerIndex = (currentPlayerIndex + 1) % playerPieces.size();
  }

  /**
   * Declares the winner of the game.
   * Increments the winning player's wins and increments losses for all other
   * players.
   * 
   * Might need to be changed to accommodate for SpecialTileManager.
   *
   * @param winner the PlayerPiece that reached the winning tile.
   */
  private void declareWinner(PlayingPiece winner) {
    Player winnerPlayer = winner.getPlayer();
    if (winnerPlayer != null) {
      // Increment wins for the winning player.
      winnerPlayer.getStats().addWin();
      System.out.println("Player " + winnerPlayer.getPlayerName() + " wins!");

      // Increment losses for every other player.
      for (PlayingPiece piece : playerPieces) {
        Player player = piece.getPlayer();
        if (player != null && piece != winner) {
          player.getStats().addLoss();
        }
      }
    } else {
      System.out.println("Error: Winner's player is null.");
    }
  }

  public boolean isGameOver() {
    return gameOver;
  }

  /**
   * Sorts the players by their age in ascending order.
   * Resets the current player index to 0 after sorting.
   */
  public void sortPlayersByAge() {
    Collections.sort(playerPieces, Comparator.comparingInt(pp -> pp.getPlayer().getPlayerAge()));
    currentPlayerIndex = 0;
  }
}
