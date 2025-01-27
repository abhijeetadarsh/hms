package online.abhijeetadarsh.hms.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Appointment appointment;

    @Column(nullable = false)
    private LocalDate prescribedDate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String medications;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String dosage;

    @Column(columnDefinition = "TEXT")
    private String instructions;
}