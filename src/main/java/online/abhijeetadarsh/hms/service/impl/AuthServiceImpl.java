package online.abhijeetadarsh.hms.service.impl;

import online.abhijeetadarsh.hms.common.User;
import online.abhijeetadarsh.hms.dto.*;
import online.abhijeetadarsh.hms.exception.AuthenticationException;
import online.abhijeetadarsh.hms.exception.InvalidCredentialsException;
import online.abhijeetadarsh.hms.exception.ResourceAlreadyExistsException;
import online.abhijeetadarsh.hms.exception.ResourceNotFoundException;
import online.abhijeetadarsh.hms.model.*;
import online.abhijeetadarsh.hms.repository.DepartmentRepository;
import online.abhijeetadarsh.hms.repository.DoctorRepository;
import online.abhijeetadarsh.hms.repository.PatientRepository;
import online.abhijeetadarsh.hms.repository.ReceptionistRepository;
import online.abhijeetadarsh.hms.security.SecurityUser;
import online.abhijeetadarsh.hms.service.AuthService;
import online.abhijeetadarsh.hms.service.JWTService;
import online.abhijeetadarsh.hms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the AuthService interface.
 */
@Service
public class AuthServiceImpl implements AuthService {
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final PatientRepository patientRepository;
    private final ReceptionistRepository receptionistRepository;
    private final UserService userService;

    @Autowired
    public AuthServiceImpl(
            JWTService jwtService,
            PasswordEncoder passwordEncoder,
            DoctorRepository doctorRepository,
            DepartmentRepository departmentRepository,
            PatientRepository patientRepository,
            ReceptionistRepository receptionistRepository,
            UserService userService
    ) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
        this.patientRepository = patientRepository;
        this.receptionistRepository = receptionistRepository;
        this.userService = userService;
    }

    @Override
    public Doctor register(RegisterDoctorRequest request) {
        if (doctorRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Doctor already exists with email: " + request.getEmail());
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.getDepartmentId()));


        Doctor doctor = new Doctor();
        doctor.setEmail(request.getEmail());
        doctor.setPassword(passwordEncoder.encode(request.getPassword()));
        doctor.setName(request.getName());
        doctor.setJoinDate(request.getJoinDate());
        doctor.setContact(request.getContact());
        doctor.setAddress(request.getAddress());
        doctor.setDepartment(department);
        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());

        doctorRepository.save(doctor);
        return doctor;
    }

    @Override
    public Patient register(RegisterPatientRequest request) {
        if (patientRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Patient already exists with email: " + request.getEmail());
        }

        Patient patient = new Patient();
        patient.setEmail(request.getEmail());
        patient.setPassword(passwordEncoder.encode(request.getPassword()));
        patient.setName(request.getName());
        patient.setDob(request.getDob());
        patient.setGender(request.getGender());
        patient.setAddress(request.getAddress());
        patient.setPhone(request.getPhone());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setEmergencyContact(request.getEmergencyContact());
        patient.setAllergies(request.getAllergies());
        patient.setMedicalHistory(request.getMedicalHistory());

        patientRepository.save(patient);
        return patient;
    }

    @Override
    public Receptionist register(RegisterReceptionistRequest request) {
        if (receptionistRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Receptionist already exists with email: " + request.getEmail());
        }

        Receptionist receptionist = new Receptionist();
        receptionist.setEmail(request.getEmail());
        receptionist.setPassword(passwordEncoder.encode(request.getPassword()));
        receptionist.setName(request.getName());
        receptionist.setJoinDate(request.getJoinDate());
        receptionist.setContact(request.getContact());
        receptionist.setAddress(request.getAddress());
        receptionist.setShift(request.getShift());
        receptionist.setDeskLocation(request.getDeskLocation());

        receptionistRepository.save(receptionist);
        return receptionist;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            SecurityUser securityUser = new SecurityUser(user);
            String token = jwtService.generateToken(securityUser);
            return LoginResponse.builder()
                    .message("Login successful")
                    .token(token)
                    .user(LoginResponse.UserData.builder()
                            .username(user.getName())
                            .email(user.getEmail())
                            .role(user.getRole())
                            .build()
                    ).build();
        }
        throw new InvalidCredentialsException("Invalid credentials");
    }

    @Override
    public User changePassword(ChangePasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof SecurityUser)) {
            throw new AuthenticationException("User not authenticated");
        }

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User currentUser = securityUser.getUser();

        if (!passwordEncoder.matches(request.getCurrentPassword(), currentUser.getPassword())) {
            throw new InvalidCredentialsException("Invalid current password");
        }
        currentUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        switch (currentUser) {
            case Doctor doctor -> doctorRepository.save(doctor);
            case Patient patient -> patientRepository.save(patient);
            case Receptionist receptionist -> receptionistRepository.save(receptionist);
            default -> {
                throw new IllegalStateException("Unexpected user type: " + currentUser.getClass().getName());
            }
        }
        return currentUser;
    }
}
