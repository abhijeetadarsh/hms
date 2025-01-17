package online.abhijeetadarsh.hms.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Prescription {
    private Long prescriptionId;
    private Long patientUserId;
    private Long doctorUserId;
    private Long appointmentId;
    private LocalDate prescribedDate;
    private String medications;
    private String dosage;
    private String instructions;
}