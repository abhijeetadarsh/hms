package online.abhijeetadarsh.hms.dto;

import jakarta.validation.constraints.*;
import lombok.Value;

import java.time.LocalDate;

@Value
public class RegisterPatientRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    String password;

    @NotBlank(message = "Name is required")
    @Size(min = 2, message = "Name must be at least 2 characters")
    String name;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    LocalDate dob;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
    String gender;

    @NotBlank(message = "Address is required")
    String address;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone must be 10 digits")
    String phone;

    @NotBlank(message = "Blood group is required")
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Invalid blood group format")
    String bloodGroup;

    @NotBlank(message = "Emergency contact is required")
    @Pattern(regexp = "^\\d{10}$", message = "Emergency contact must be 10 digits")
    String emergencyContact;

    @Size(max = 500, message = "Allergies description too long")
    String allergies;

    @Size(max = 1000, message = "Medical history description too long")
    String medicalHistory;
}
