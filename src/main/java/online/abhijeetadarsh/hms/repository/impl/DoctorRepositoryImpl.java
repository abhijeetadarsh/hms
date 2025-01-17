package online.abhijeetadarsh.hms.repository.impl;

import online.abhijeetadarsh.hms.model.Doctor;
import online.abhijeetadarsh.hms.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class DoctorRepositoryImpl implements DoctorRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_BY_ID =
            "SELECT doctor_id, username, password, email, created_at, is_active FROM doctor WHERE doctor_id = ?";
    private static final String SELECT_ALL =
            "SELECT doctor_id, username, password, email, created_at, is_active FROM doctor";
    private static final String INSERT =
            "INSERT INTO doctor (username, password, email, is_active) VALUES (?, ?, ?, ?)";
    private static final String UPDATE =
            "UPDATE doctor SET username = ?, password = ?, email = ?, is_active = ? WHERE doctor_id = ?";
    private static final String DELETE =
            "DELETE FROM doctor WHERE doctor_id = ?";
    private static final String SELECT_BY_USERNAME =
            "SELECT doctor_id, username, password, email, created_at, is_active FROM doctor WHERE username = ?";
    private static final String SELECT_BY_EMAIL =
            "SELECT doctor_id, username, password, email, created_at, is_active FROM doctor WHERE email = ?";

    @Autowired
    public DoctorRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Doctor findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID, this::mapRowToDoctor, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Doctor> findAll() {
        return jdbcTemplate.query(SELECT_ALL, this::mapRowToDoctor);
    }

    @Override
    public int save(Doctor doctor) {
        return jdbcTemplate.update(INSERT,
                doctor.getUsername(),
                doctor.getPassword(),
                doctor.getEmail(),
                doctor.isActive()
        );
    }

    @Override
    public int update(Doctor doctor) {
        return jdbcTemplate.update(UPDATE,
                doctor.getUsername(),
                doctor.getPassword(),
                doctor.getEmail(),
                doctor.isActive(),
                doctor.getUserId()
        );
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update(DELETE, id);
    }

    @Override
    public Optional<Doctor> findByUsername(String username) {
        try {
            Doctor doctor = jdbcTemplate.queryForObject(SELECT_BY_USERNAME, this::mapRowToDoctor, username);
            return Optional.ofNullable(doctor);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Doctor> findByEmail(String email) {
        try {
            Doctor doctor = jdbcTemplate.queryForObject(SELECT_BY_EMAIL, this::mapRowToDoctor, email);
            return Optional.ofNullable(doctor);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private Doctor mapRowToDoctor(ResultSet rs, int rowNum) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setUserId(rs.getLong("doctor_id"));
        doctor.setUsername(rs.getString("username"));
        doctor.setPassword(rs.getString("password"));
        doctor.setEmail(rs.getString("email"));
        doctor.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        doctor.setActive(rs.getBoolean("is_active"));
        return doctor;
    }
}