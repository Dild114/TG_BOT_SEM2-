package app.api.exception;

public class ResourseNotFoundException extends RuntimeException {
  public ResourseNotFoundException(String message) {
    super(message);
  }
}
