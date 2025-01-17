package online.abhijeetadarsh.hms.repository.impl;

import online.abhijeetadarsh.hms.model.Patient;
import online.abhijeetadarsh.hms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepositoryImpl implements PatientRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_BY_ID =
            "SELECT patient_id, username, password, email, created_at, is_active FROM patient WHERE patient_id = ?";
    private static final String SELECT_ALL =
            "SELECT patient_id, username, password, email, created_at, is_active FROM patient";
    private static final String INSERT =
            "INSERT INTO patient (username, password, email, is_active) VALUES (?, ?, ?, ?)";
    private static final String UPDATE =
            "UPDATE patient SET username = ?, password = ?, email = ?, is_active = ? WHERE patient_id = ?";
    private static final String DELETE =
            "DELETE FROM patient WHERE patient_id = ?";
    private static final String SELECT_BY_USERNAME =
            "SELECT patient_id, username, password, email, created_at, is_active FROM patient WHERE username = ?";
    private static final String SELECT_BY_EMAIL =
            "SELECT patient_id, username, password, email, created_at, is_active FROM patient WHERE email = ?";

    @Autowired
    public PatientRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Patient findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID, this::mapRowToPatient, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Patient> findAll() {
        return jdbcTemplate.query(SELECT_ALL, this::mapRowToPatient);
    }

    @Override
    public int save(Patient patient) {
        return jdbcTemplate.update(INSERT,
                patient.getUsername(),
                patient.getPassword(),
                patient.getEmail(),
                patient.isActive()
        );
    }

    @Override
    public int update(Patient patient) {
        return jdbcTemplate.update(UPDATE,
                patient.getUsername(),
                patient.getPassword(),
                patient.getEmail(),
                patient.isActive(),
                patient.getUserId()
        );
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update(DELETE, id);
    }

    @Override
    public Optional<Patient> findByUsername(String username) {
        try {
            Patient patient = jdbcTemplate.queryForObject(SELECT_BY_USERNAME, this::mapRowToPatient, username);
            return Optional.ofNullable(patient);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        try {
            Patient patient = jdbcTemplate.queryForObject(SELECT_BY_EMAIL, this::mapRowToPatient, email);
            return Optional.ofNullable(patient);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private Patient mapRowToPatient(ResultSet rs, int rowNum) throws SQLException {
        Patient patient = new Patient();
        patient.setUserId(rs.getLong("patient_id"));
        patient.setUsername(rs.getString("username"));
        patient.setPassword(rs.getString("password"));
        patient.setEmail(rs.getString("email"));
        patient.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        patient.setActive(rs.getBoolean("is_active"));
        return patient;
    }
}