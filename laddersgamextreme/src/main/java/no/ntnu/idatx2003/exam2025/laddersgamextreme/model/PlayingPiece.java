package no.ntnu.idatx2003.exam2025.laddersgamextreme.model;

import javafx.scene.paint.Color;

/**
 * Represents a playing piece in the game.
 * Each piece is associated with a player and has a color and a current tile
 * position.
 *
 * @author Dennis Moe
 */
public class PlayingPiece {
  private final Player player; // Owner of piece
  private int currentTile; // 1-based number position
  private Color pieceColor; // Color of players piece

  /**
   * Constructs a new PlayingPiece.
   *
   * @param player     the player who owns this piece
   * @param pieceColor the color of the piece
   */
  public PlayingPiece(Player player, Color pieceColor) {
    this.player = player;
    this.pieceColor = pieceColor;
    this.currentTile = 0; // Starting tile. Player's start outside the board
  }

  public Player getPlayer() {
    return player;
  }

  public int getCurrentTile() {
    return currentTile;
  }

  public void setCurrentTile(int currentTile) {
    this.currentTile = currentTile;
  }

  /**
   * Updates the player position and checks boundries.
   * 
   * <p>
   * To "simulate" players hopping from tile to tile this should be changed to
   * refresh the board for each step. -> for loop for i amount of steps?
   * ^ This would activate ladders/snakes...
   * </p>
   *
   * @param steps   The steps the player will move.
   * @param maxTile Rows * Cols = max boundry of board.
   */
  public void move(int steps, int maxTile) {
    int newTile = currentTile + steps;

    if (newTile < 1) {
      newTile = 1;
      System.err.println("Error: currentTile went below 1. Resetting to 1.");
    }
    if (newTile > maxTile) {
      newTile = maxTile;
      System.out.println("Player reached a higher tile than allows, set position to maxTile");
    }
    currentTile = newTile;

  }

  public Color getPieceColor() {
    return pieceColor;
  }

  public void setPieceColor(Color pieceColor) {
    this.pieceColor = pieceColor;
  }
}
