package online.abhijeetadarsh.hms.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Appointment {
    private Long appointmentId;
    private Long patientUserId;
    private Long doctorUserId;
    private Long bookedByUserId;
    private LocalDateTime appointmentTime;
    private String status;
    private String reason;
    private LocalDateTime bookingTime;
    private Integer tokenNumber;
    private Double consultationFee;
}
