package online.abhijeetadarsh.hms.exception;

/**
 * Exception thrown for database constraints violations like foreign key or unique key conflicts.
 */
public class DataIntegrityViolationException extends RuntimeException {
    public DataIntegrityViolationException(String message) {
        super(message);
    }
}
