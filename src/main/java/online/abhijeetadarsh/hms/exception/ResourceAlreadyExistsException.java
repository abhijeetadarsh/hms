package online.abhijeetadarsh.hms.exception;

/**
 * Exception thrown when attempting to add a duplicate resource.
 */
public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
