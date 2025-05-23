package no.ntnu.idatx2003.exam2025.boardgames.exception;

/**
 * A Checked Exception used to catch an issue where GamePieces might not be correctly assigned
 * on game start.
 */
public class MissingGamePieceException extends Exception {
  /**
   * A constructor for building a MissingGamePiece exception
   *
   * @param message requires a String message for error tracking.
   */
  public MissingGamePieceException(String message) {
    super(message);
  }
}
