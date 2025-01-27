package online.abhijeetadarsh.hms.dto;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Error response structure for consistent error handling
 */
@Value
@Builder
public class ErrorResponse {
    HttpStatus status;
    String message;
    List<String> details;
    LocalDateTime timestamp;
}
