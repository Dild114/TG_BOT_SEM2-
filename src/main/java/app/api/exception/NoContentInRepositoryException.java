package app.api.exception;

public class NoContentInRepositoryException extends RuntimeException {
  public NoContentInRepositoryException(String message) {
    super(message);
  }
}
