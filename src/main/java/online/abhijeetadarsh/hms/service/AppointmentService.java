package online.abhijeetadarsh.hms.service;

import online.abhijeetadarsh.hms.dto.AppointmentRequest;
import online.abhijeetadarsh.hms.dto.AppointmentResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {
    private final JdbcTemplate jdbcTemplate;

    public AppointmentService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class AppointmentRowMapper implements RowMapper<AppointmentResponse> {
        @Override
        public AppointmentResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            AppointmentResponse response = new AppointmentResponse();
            response.setAppointmentId(rs.getLong("appointment_id"));
            response.setPatientName(rs.getString("patient_name"));
            response.setDoctorName(rs.getString("doctor_name"));
            response.setAppointmentTime(rs.getTimestamp("appointment_time").toLocalDateTime());
            response.setStatus(rs.getString("status"));
            response.setHomeVisit(rs.getBoolean("is_home_visit"));
            return response;
        }
    }

    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        // Check if doctor exists
        Integer doctorCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM doctors WHERE doctor_id = ?",
                Integer.class,
                request.getDoctorId()
        );
        if (doctorCount == null || doctorCount == 0) {
            throw new RuntimeException("Doctor not found");
        }

        // Check if patient exists
        Integer patientCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM patients WHERE patient_id = ?",
                Integer.class,
                request.getPatientId()
        );
        if (patientCount == null || patientCount == 0) {
            throw new RuntimeException("Patient not found");
        }

        // Check doctor availability
        if (!isDoctorAvailable(request.getDoctorId(), request.getAppointmentTime())) {
            throw new RuntimeException("Doctor is not available at this time");
        }

        // Insert appointment
        String sql = """
            INSERT INTO appointments (
                patient_id, doctor_id, appointment_time, 
                symptom_description, is_home_visit, status, document_urls
            ) VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        jdbcTemplate.update(sql,
                request.getPatientId(),
                request.getDoctorId(),
                request.getAppointmentTime(),
                request.getSymptomDescription(),
                request.isHomeVisit(),
                "SCHEDULED",
                request.getDocumentUrls()
        );

        // Fetch and return the created appointment
        return getLatestAppointment(request.getPatientId());
    }

    private boolean isDoctorAvailable(Long doctorId, LocalDateTime requestedTime) {
        String sql = """
            SELECT COUNT(*) FROM appointments 
            WHERE doctor_id = ? 
            AND appointment_time BETWEEN ? AND ?
            AND status = 'SCHEDULED'
            """;

        LocalDateTime startTime = requestedTime.minusMinutes(30);
        LocalDateTime endTime = requestedTime.plusMinutes(30);

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class,
                doctorId, startTime, endTime);

        return count != null && count == 0;
    }

    private AppointmentResponse getLatestAppointment(Long patientId) {
        String sql = """
            SELECT 
                a.appointment_id, 
                p.name as patient_name,
                d.name as doctor_name,
                a.appointment_time,
                a.status,
                a.is_home_visit
            FROM appointments a
            JOIN patients p ON a.patient_id = p.patient_id
            JOIN doctors d ON a.doctor_id = d.doctor_id
            WHERE a.patient_id = ?
            ORDER BY a.appointment_id DESC
            LIMIT 1
            """;

        return jdbcTemplate.queryForObject(sql, new AppointmentRowMapper(), patientId);
    }

    public List<AppointmentResponse> getPatientAppointments(Long patientId) {
        String sql = """
            SELECT 
                a.appointment_id, 
                p.name as patient_name,
                d.name as doctor_name,
                a.appointment_time,
                a.status,
                a.is_home_visit
            FROM appointments a
            JOIN patients p ON a.patient_id = p.patient_id
            JOIN doctors d ON a.doctor_id = d.doctor_id
            WHERE a.patient_id = ?
            ORDER BY a.appointment_time DESC
            """;

        return jdbcTemplate.query(sql, new AppointmentRowMapper(), patientId);
    }

    public List<AppointmentResponse> getDoctorAppointments(Long doctorId) {
        String sql = """
            SELECT 
                a.appointment_id, 
                p.name as patient_name,
                d.name as doctor_name,
                a.appointment_time,
                a.status,
                a.is_home_visit
            FROM appointments a
            JOIN patients p ON a.patient_id = p.patient_id
            JOIN doctors d ON a.doctor_id = d.doctor_id
            WHERE a.doctor_id = ? AND a.status = 'SCHEDULED'
            ORDER BY a.appointment_time
            """;

        return jdbcTemplate.query(sql, new AppointmentRowMapper(), doctorId);
    }
}