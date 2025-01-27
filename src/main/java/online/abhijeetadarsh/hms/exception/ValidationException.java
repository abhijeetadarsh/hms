package online.abhijeetadarsh.hms.exception;

/**
 * Exception thrown for input data that fails validation rules.
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
