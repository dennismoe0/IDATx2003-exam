package no.ntnu.idatx2003.exam2025.boardgames.exception;

public class InvalidUserDataException extends RuntimeException {
  public InvalidUserDataException(String message) {
    super(message);
  }
}
