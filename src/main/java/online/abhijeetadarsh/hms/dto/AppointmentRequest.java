package online.abhijeetadarsh.hms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    private Long patientId;
    private Long doctorId;
    private LocalDateTime appointmentTime;
    private String symptomDescription;
    private boolean isHomeVisit;
    private String documentUrls; // Comma-separated URLs of uploaded documents
}