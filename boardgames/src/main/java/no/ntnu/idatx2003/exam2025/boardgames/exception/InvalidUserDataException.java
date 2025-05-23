package no.ntnu.idatx2003.exam2025.boardgames.exception;

/**
 * Represents a checked Exception for when Data that could cause errors is found.
 */
public class InvalidUserDataException extends RuntimeException {

  /**
   * Default constructor for Invalid User Data Exception.
   *
   * @param message a string object representing the message to be delivered.
   */
  public InvalidUserDataException(String message) {
    super(message);
  }
}
