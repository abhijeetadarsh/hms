package online.abhijeetadarsh.hms.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Billing {
    private Long billId;
    private Long patientUserId;
    private Long appointmentId;
    private LocalDate billDate;
    private Double consultationFee;
    private Double medicineCharges;
    private Double labCharges;
    private Double totalAmount;
    private String paymentStatus;
    private String paymentMethod;
}