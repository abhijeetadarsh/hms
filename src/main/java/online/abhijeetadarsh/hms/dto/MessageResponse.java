package online.abhijeetadarsh.hms.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class MessageResponse {
    boolean success;
    String message;
    String id;
    String email;
    String name;
    String role;
    LocalDateTime timestamp;
}
