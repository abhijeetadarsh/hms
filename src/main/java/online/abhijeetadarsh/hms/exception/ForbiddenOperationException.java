package online.abhijeetadarsh.hms.exception;

/**
 * Exception thrown when a user tries to perform a restricted action.
 */
public class ForbiddenOperationException extends RuntimeException {
    public ForbiddenOperationException(String message) {
        super(message);
    }
}
