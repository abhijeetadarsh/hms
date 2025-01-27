package online.abhijeetadarsh.hms.exception;

/**
 * Exception thrown when multiple users attempt conflicting updates at the same time.
 */
public class ConcurrencyException extends RuntimeException {
    public ConcurrencyException(String message) {
        super(message);
    }
}
