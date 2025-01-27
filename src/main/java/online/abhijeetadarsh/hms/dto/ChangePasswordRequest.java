package online.abhijeetadarsh.hms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class ChangePasswordRequest {
    @NotBlank(message = "Password is required")
    String currentPassword;

    @NotBlank(message = "newPassword is required")
    @Size(min = 8, message = "newPassword must be at least 8 characters")
    String newPassword;
}
