package online.abhijeetadarsh.hms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private int id;
    private String username;
    private String role;
}

