package online.abhijeetadarsh.hms.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private int id;
    private String username;
    private String password; // Store hashed passwords
    private String email;
    private String name;
    private String gender;
    private String contactNumber;
    private String address;
    private String role; // e.g., "ADMIN", "DOCTOR", "PATIENT", "STAFF"
    private LocalDateTime createdAt;
}
