package online.abhijeetadarsh.hms.repository.impl;

import online.abhijeetadarsh.hms.model.Receptionist;
import online.abhijeetadarsh.hms.repository.ReceptionistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ReceptionistRepositoryImpl implements ReceptionistRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_BY_ID =
            "SELECT receptionist_id, username, password, email, created_at, is_active FROM receptionist WHERE receptionist_id = ?";
    private static final String SELECT_ALL =
            "SELECT receptionist_id, username, password, email, created_at, is_active FROM receptionist";
    private static final String INSERT =
            "INSERT INTO receptionist (username, password, email, is_active) VALUES (?, ?, ?, ?)";
    private static final String UPDATE =
            "UPDATE receptionist SET username = ?, password = ?, email = ?, is_active = ? WHERE receptionist_id = ?";
    private static final String DELETE =
            "DELETE FROM receptionist WHERE receptionist_id = ?";
    private static final String SELECT_BY_USERNAME =
            "SELECT receptionist_id, username, password, email, created_at, is_active FROM receptionist WHERE username = ?";
    private static final String SELECT_BY_EMAIL =
            "SELECT receptionist_id, username, password, email, created_at, is_active FROM receptionist WHERE email = ?";

    @Autowired
    public ReceptionistRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Receptionist findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID, this::mapRowToReceptionist, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Receptionist> findAll() {
        return jdbcTemplate.query(SELECT_ALL, this::mapRowToReceptionist);
    }

    @Override
    public int save(Receptionist receptionist) {
        return jdbcTemplate.update(INSERT,
                receptionist.getUsername(),
                receptionist.getPassword(),
                receptionist.getEmail(),
                receptionist.isActive()
        );
    }

    @Override
    public int update(Receptionist receptionist) {
        return jdbcTemplate.update(UPDATE,
                receptionist.getUsername(),
                receptionist.getPassword(),
                receptionist.getEmail(),
                receptionist.isActive(),
                receptionist.getUserId()
        );
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update(DELETE, id);
    }

    @Override
    public Optional<Receptionist> findByUsername(String username) {
        try {
            Receptionist receptionist = jdbcTemplate.queryForObject(SELECT_BY_USERNAME, this::mapRowToReceptionist, username);
            return Optional.ofNullable(receptionist);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Receptionist> findByEmail(String email) {
        try {
            Receptionist receptionist = jdbcTemplate.queryForObject(SELECT_BY_EMAIL, this::mapRowToReceptionist, email);
            return Optional.ofNullable(receptionist);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private Receptionist mapRowToReceptionist(ResultSet rs, int rowNum) throws SQLException {
        Receptionist receptionist = new Receptionist();
        receptionist.setUserId(rs.getLong("receptionist_id"));
        receptionist.setUsername(rs.getString("username"));
        receptionist.setPassword(rs.getString("password"));
        receptionist.setEmail(rs.getString("email"));
        receptionist.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        receptionist.setActive(rs.getBoolean("is_active"));
        return receptionist;
    }
}