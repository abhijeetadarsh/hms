package online.abhijeetadarsh.hms.dto;

import jakarta.validation.constraints.*;
import lombok.Value;

import java.time.LocalDate;

@Value
public class RegisterDoctorRequest {
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
    @Pattern(regexp = "^\\+?[1-9][0-9]{7,14}$", message = "Invalid contact number")
    String contact;

    @NotBlank(message = "Address is required")
    String address;

    @NotBlank(message = "Department ID is required")
    String departmentId;

    @NotBlank(message = "Specialization is required")
    String specialization;

    @NotBlank(message = "Qualification is required")
    String qualification;
}