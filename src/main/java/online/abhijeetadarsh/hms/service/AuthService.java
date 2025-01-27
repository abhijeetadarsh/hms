package online.abhijeetadarsh.hms.service;

import online.abhijeetadarsh.hms.dto.*;
import online.abhijeetadarsh.hms.model.Doctor;
import online.abhijeetadarsh.hms.model.Patient;
import online.abhijeetadarsh.hms.model.Receptionist;
import online.abhijeetadarsh.hms.common.User;

/**
 * Service interface for authentication-related operations.
 */
public interface AuthService {

    /**
     * Registers a new doctor.
     *
     * @param request the registration request containing doctor details
     * @return a message indicating the result of the registration
     */
    Doctor register(RegisterDoctorRequest request);

    /**
     * Registers a new patient.
     *
     * @param request the registration request containing patient details
     * @return a message indicating the result of the registration
     */
    Patient register(RegisterPatientRequest request);

    /**
     * Registers a new receptionist.
     *
     * @param request the registration request containing receptionist details
     * @return a message indicating the result of the registration
     */
    Receptionist register(RegisterReceptionistRequest request);

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param request the login request containing username and password
     * @return the login response containing the JWT token
     */
    LoginResponse login(LoginRequest request);

    /**
     * Changes the password of a user.
     *
     * @param request the change password request containing old and new passwords
     * @return a message indicating the result of the password change
     */
    User changePassword(ChangePasswordRequest request);
}