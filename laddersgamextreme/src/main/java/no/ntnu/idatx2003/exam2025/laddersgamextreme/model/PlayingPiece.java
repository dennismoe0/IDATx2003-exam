package no.ntnu.idatx2003.exam2025.laddersgamextreme.model;

import javafx.scene.paint.Color;

public class PlayingPiece {
  private final Player player; // Owner of piece
  private int currentTile; // 1-based number position
  private Color pieceColor; // Color of players piece

  public PlayingPiece(Player player, Color pieceColor) {
    this.player = player;
    this.pieceColor = pieceColor;
    this.currentTile = 1; // Starting tile.
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
    }
    currentTile = newTile;
    // need to also make a check here for ABOVE max tile number allowed for size.
    // Also need a class for game state bc the player wins if above the max allowed
    // tile position.
  }

  public Color getPieceColor() {
    return pieceColor;
  }

  public void setPieceColor(Color pieceColor) {
    this.pieceColor = pieceColor;
  }
}
