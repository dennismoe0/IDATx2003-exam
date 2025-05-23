package no.ntnu.idatx2003.exam2025.boardgames.exception;

/**
 * Checked Exception used to catch issues where an incorrect number of dice might be given.
 */
public class InvalidDiceAmountException extends Exception {
  /**
   * Constructs an InvalidDiceAmountException object.
   *
   * @param message is a String representing the message to be delivered.
   */
  public InvalidDiceAmountException(String message) {
    super(message);
  }
}
