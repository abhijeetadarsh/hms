package online.abhijeetadarsh.hms.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String role;
    private LocalDateTime createdAt;
    private boolean isActive;
}
