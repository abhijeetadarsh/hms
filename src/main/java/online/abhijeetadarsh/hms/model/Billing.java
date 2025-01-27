package online.abhijeetadarsh.hms.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Patient patient;

    @OneToOne
    @JoinColumn(nullable = false)
    private Appointment appointment;

    @Column(nullable = false)
    private LocalDate billDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal consultationFee;

    @Column(precision = 10, scale = 2)
    private BigDecimal medicineCharges;

    @Column(precision = 10, scale = 2)
    private BigDecimal labCharges;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Column(length = 50)
    private String paymentMethod;

    public enum PaymentStatus {
        Pending,
        Paid,
        Cancelled,
        Refunded
    }
}

