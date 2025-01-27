package online.abhijeetadarsh.hms.exception;

/**
 * Exception thrown when a user tries to access a resource they’re not authorized for.
 */
public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
