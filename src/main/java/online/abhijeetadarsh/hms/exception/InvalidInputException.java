package online.abhijeetadarsh.hms.exception;

/**
 * Exception thrown when the user provides invalid input.
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}
