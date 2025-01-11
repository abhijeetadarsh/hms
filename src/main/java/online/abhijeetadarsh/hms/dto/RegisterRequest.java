package online.abhijeetadarsh.hms.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String name;
    private String gender;
    private String contactNumber;
    private String address;
    private String role;
}
