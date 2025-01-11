package online.abhijeetadarsh.hms.service;

import online.abhijeetadarsh.hms.dto.LoginResponse;
import online.abhijeetadarsh.hms.exception.UnauthorizedException;
import online.abhijeetadarsh.hms.model.User;
import online.abhijeetadarsh.hms.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AuthService {
    private final JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Autowired
    public AuthService(JdbcTemplate jdbcTemplate, JWTService jwtService) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtService = jwtService;
    }

    public void register(String username, String password, String email, String name, String gender, String contactNumber, String address, String role) {
        String hashedPassword = passwordEncoder.encode(password);

        // Set default role if none provided
        if (role == null) {
            role = "PATIENT";
        }

        // Validate role
        if (!Arrays.asList("ADMIN", "DOCTOR", "PATIENT", "STAFF").contains(role)) {
            throw new IllegalArgumentException("Invalid role. Must be ADMIN, DOCTOR, PATIENT, or STAFF");
        }

        // Validate gender
        if (!Arrays.asList("MALE", "FEMALE", "OTHER").contains(gender)) {
            throw new IllegalArgumentException("Invalid gender. Must be MALE, FEMALE, or OTHER");
        }

        String sql = "INSERT INTO User (username, password, email, name, gender, contactNumber, address, role) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, username, hashedPassword, email, name, gender, contactNumber, address, role);
    }

    public LoginResponse login(String username, String password) {
        String sql = "SELECT * FROM User WHERE username = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new UserService.UserRowMapper(), username);
            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                String token = jwtService.generateToken(user);
                return new LoginResponse(token, user.getId(), user.getUsername(), user.getRole());
            }
        } catch (EmptyResultDataAccessException e) {
            throw new UnauthorizedException("Invalid credentials");
        }
        throw new UnauthorizedException("Invalid credentials");
    }
}
