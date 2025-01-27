package online.abhijeetadarsh.hms.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginResponse {
    String message;
    String token;
    UserData user;

    @Value
    @Builder
    public static class UserData {
        String username;
        String email;
        String role;
    }
}