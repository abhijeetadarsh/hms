package online.abhijeetadarsh.hms.repository.impl;

import online.abhijeetadarsh.hms.model.Staff;
import online.abhijeetadarsh.hms.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class StaffRepositoryImpl implements StaffRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_BY_ID =
            "SELECT staff_id, username, password, email, created_at, is_active FROM staff WHERE staff_id = ?";
    private static final String SELECT_ALL =
            "SELECT staff_id, username, password, email, created_at, is_active FROM staff";
    private static final String INSERT =
            "INSERT INTO staff (username, password, email, is_active) VALUES (?, ?, ?, ?)";
    private static final String UPDATE =
            "UPDATE staff SET username = ?, password = ?, email = ?, is_active = ? WHERE staff_id = ?";
    private static final String DELETE =
            "DELETE FROM staff WHERE staff_id = ?";
    private static final String SELECT_BY_USERNAME =
            "SELECT staff_id, username, password, email, created_at, is_active FROM staff WHERE username = ?";
    private static final String SELECT_BY_EMAIL =
            "SELECT staff_id, username, password, email, created_at, is_active FROM staff WHERE email = ?";

    @Autowired
    public StaffRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Staff findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID, this::mapRowToStaff, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Staff> findAll() {
        return jdbcTemplate.query(SELECT_ALL, this::mapRowToStaff);
    }

    @Override
    public int save(Staff staff) {
        return jdbcTemplate.update(INSERT,
                staff.getUsername(),
                staff.getPassword(),
                staff.getEmail(),
                staff.isActive()
        );
    }

    @Override
    public int update(Staff staff) {
        return jdbcTemplate.update(UPDATE,
                staff.getUsername(),
                staff.getPassword(),
                staff.getEmail(),
                staff.isActive(),
                staff.getUserId()
        );
    }

    @Override
    public int delete(Long id) {
        return jdbcTemplate.update(DELETE, id);
    }

    @Override
    public Optional<Staff> findByUsername(String username) {
        try {
            Staff staff = jdbcTemplate.queryForObject(SELECT_BY_USERNAME, this::mapRowToStaff, username);
            return Optional.ofNullable(staff);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Staff> findByEmail(String email) {
        try {
            Staff staff = jdbcTemplate.queryForObject(SELECT_BY_EMAIL, this::mapRowToStaff, email);
            return Optional.ofNullable(staff);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private Staff mapRowToStaff(ResultSet rs, int rowNum) throws SQLException {
        Staff staff = new Staff();
        staff.setUserId(rs.getLong("staff_id"));
        staff.setUsername(rs.getString("username"));
        staff.setPassword(rs.getString("password"));
        staff.setEmail(rs.getString("email"));
        staff.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        staff.setActive(rs.getBoolean("is_active"));
        return staff;
    }
}