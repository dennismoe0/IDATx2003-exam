package no.ntnu.idatx2003.exam2025.laddersgamextreme.model;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages a single session of the board game.
 * Holds BoardModel for backend definitions.
 * PlayerPieces for the tokens and handles turn order etc.
 * 
 * @author Dennis Moe
 */
public class GameSession {
  private final BoardModel board;
  private final List<PlayingPiece> playerPieces; // Stores players in the game.
                                                 // Position is stored in the object.
  private int currentPlayerIndex; // Which player has an active turn.
  private boolean gameOver; // I.e. if a player reaches 100 it goes to true.
                            // the player at the maxTile at the end wins.

  public GameSession(BoardModel board) {
    this.board = board;
    this.playerPieces = new ArrayList<>();
    this.currentPlayerIndex = 0;
    this.gameOver = false;
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

  /**
   * Moves the current player's piece by the specified number of steps.
   * After moving, the method checks if the player has reached the final tile.
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

    // Check if the current player has reached the winning tile.
    if (currentPiece.getCurrentTile() == maxTile) {
      declareWinner(currentPiece);
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
   * @param winner the PlayerPiece that reached the winning tile.
   */
  private void declareWinner(PlayingPiece winner) {
    // Increment wins for the winning player.
    winner.getPlayer().getStats().addWin();
    System.out.println("Player " + winner.getPlayer().getPlayerName() + " wins!");

    // Increment losses for every other player.
    for (PlayingPiece piece : playerPieces) {
      if (piece != winner) {
        piece.getPlayer().getStats().addLoss();
      }
    }
  }

  public boolean isGameOver() {
    return gameOver;
  }
}
