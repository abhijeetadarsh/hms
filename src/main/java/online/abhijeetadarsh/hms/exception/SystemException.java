package online.abhijeetadarsh.hms.exception;

/**
 * Exception thrown for unexpected server-side errors.
 */
public class SystemException extends RuntimeException {
    public SystemException(String message) {
        super(message);
    }
}
