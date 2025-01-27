package online.abhijeetadarsh.hms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.time.LocalDate;

@Value
public class RegisterReceptionistRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    String password;

    @NotBlank(message = "Name is required")
    String name;

    @NotNull(message = "Join date is required")
    LocalDate joinDate;

    @NotBlank(message = "Contact is required")
    @Size(min = 10, max = 10, message = "Contact must be 10 digits")
    String contact;

    @NotBlank(message = "Address is required")
    String address;

    @NotBlank(message = "Shift is required")
    @Size(min = 3, max = 20, message = "Shift must be between 3 and 20 characters")
    String shift;

    @NotBlank(message = "Desk location is required")
    @Size(min = 2, max = 50, message = "Desk location must be between 2 and 50 characters")
    String deskLocation;
}
