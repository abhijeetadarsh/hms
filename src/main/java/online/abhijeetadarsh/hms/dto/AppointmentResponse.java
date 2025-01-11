package online.abhijeetadarsh.hms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentResponse {
    private Long appointmentId;
    private String patientName;
    private String doctorName;
    private LocalDateTime appointmentTime;
    private String status;
    private boolean isHomeVisit;
}
