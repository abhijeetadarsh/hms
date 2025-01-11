package online.abhijeetadarsh.hms.service;

import online.abhijeetadarsh.hms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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


    public static class UserRowMapper implements RowMapper<User> {
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
