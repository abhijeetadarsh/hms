package online.abhijeetadarsh.hms.controller;

import jakarta.validation.Valid;
import online.abhijeetadarsh.hms.dto.*;
import online.abhijeetadarsh.hms.model.Doctor;
import online.abhijeetadarsh.hms.model.Patient;
import online.abhijeetadarsh.hms.model.Receptionist;
import online.abhijeetadarsh.hms.common.User;
import online.abhijeetadarsh.hms.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterDoctorRequest request) {
        Doctor doctor = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createRegistrationResponse(doctor, "Doctor registered successfully"));
    }

    @PostMapping("/register/patient")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterPatientRequest request) {
        Patient patient = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createRegistrationResponse(patient, "Patient registered successfully"));
    }

    @PostMapping("/register/receptionist")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterReceptionistRequest request) {
        Receptionist receptionist = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createRegistrationResponse(receptionist, "Receptionist registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PutMapping("/password")
    public ResponseEntity<MessageResponse> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        User user = authService.changePassword(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createRegistrationResponse(user, "Password changed successfully"));
    }

    /**
     * Creates a standardized registration response.
     */
    private MessageResponse createRegistrationResponse(User user, String message) {
        return MessageResponse.builder()
                .success(true)
                .message(message)
                .id(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .timestamp(LocalDateTime.now())
                .build();
    }
}