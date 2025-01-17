package online.abhijeetadarsh.hms.repository.impl;

import online.abhijeetadarsh.hms.model.User;
import online.abhijeetadarsh.hms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_BY_ID =
            "SELECT * FROM user WHERE user_id = ?";
    private static final String SELECT_ALL =
            "SELECT * FROM user";
    private static final String INSERT =
            "INSERT INTO user (username, password, email, role) VALUES (?, ?, ? ,?)";
    private static final String UPDATE =
            "UPDATE user SET password = ?, is_active = ? WHERE user_id = ?";
    private static final String DELETE =
            "DELETE FROM user WHERE user_id = ?";
    private static final String SELECT_BY_USERNAME =
            "SELECT * FROM user WHERE username = ?";
    private static final String SELECT_BY_EMAIL =
            "SELECT * FROM user WHERE email = ?";

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            User user = jdbcTemplate.queryForObject(SELECT_BY_ID, this::mapRowToUser, id);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SELECT_ALL, this::mapRowToUser);
    }

    @Override
    public void save(User user) {
        jdbcTemplate.update(INSERT,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole()
        );
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(UPDATE,
                user.getPassword(),
                user.isActive(),
                user.getUserId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            User user = jdbcTemplate.queryForObject(SELECT_BY_USERNAME, this::mapRowToUser, username);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            User user = jdbcTemplate.queryForObject(SELECT_BY_EMAIL, this::mapRowToUser, email);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getLong("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("role"));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        user.setActive(rs.getBoolean("is_active"));
        return user;
    }
}
