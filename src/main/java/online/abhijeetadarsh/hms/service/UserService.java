package online.abhijeetadarsh.hms.service;

import online.abhijeetadarsh.hms.dto.LoginResponse;
import online.abhijeetadarsh.hms.exception.UnauthorizedException;
import online.abhijeetadarsh.hms.model.User;
import online.abhijeetadarsh.hms.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    private final JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Autowired
    public UserService(JdbcTemplate jdbcTemplate, JWTService jwtService) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtService = jwtService;
    }

    public void registerUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());

        // Set default role if none provided
        if (user.getRole() == null) {
            user.setRole("PATIENT");
        }

        // Validate role
        if (!Arrays.asList("ADMIN", "DOCTOR", "PATIENT", "STAFF").contains(user.getRole())) {
            throw new IllegalArgumentException("Invalid role. Must be ADMIN, DOCTOR, PATIENT, or STAFF");
        }

        // Validate gender
        if (!Arrays.asList("MALE", "FEMALE", "OTHER").contains(user.getGender())) {
            throw new IllegalArgumentException("Invalid gender. Must be MALE, FEMALE, or OTHER");
        }

        String sql = "INSERT INTO User (username, password, email, name, gender, contactNumber, address, role) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), hashedPassword,  // Use the hashed password
                user.getEmail(), user.getName(), user.getGender(), user.getContactNumber(), user.getAddress(), user.getRole());
    }

    public LoginResponse loginUser(String username, String password) {
        String sql = "SELECT * FROM User WHERE username = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), username);
            if (user != null && passwordEncoder.matches(password, new String(user.getPassword()))) {
                String token = jwtService.generateToken(user);
                return new LoginResponse(token, user.getId(), user.getUsername(), user.getRole());
            }
        } catch (EmptyResultDataAccessException e) {
            throw new UnauthorizedException("Invalid credentials");
        }
        throw new UnauthorizedException("Invalid credentials");
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM User";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM User WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public boolean updateUser(int id, User user) {
        String sql = "UPDATE User SET username = ?, password = ?, email = ?, name = ?, gender = ?, " + "contactNumber = ?, address = ?, role = ? WHERE id = ?";
        return jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail(), user.getName(), user.getGender(), user.getContactNumber(), user.getAddress(), user.getRole(), id) > 0;
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM User WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
    }


    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setName(rs.getString("name"));
            user.setGender(rs.getString("gender"));
            user.setContactNumber(rs.getString("contactNumber"));
            user.setAddress(rs.getString("address"));
            user.setRole(rs.getString("role"));
            user.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
            return user;
        }
    }
}
