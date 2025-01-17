package online.abhijeetadarsh.hms.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicalRecord {
    private Long recordId;
    private Long patientUserId;
    private Long doctorUserId;
    private LocalDate recordDate;
    private String diagnosis;
    private String treatment;
    private String notes;
    private String vitals;
}
