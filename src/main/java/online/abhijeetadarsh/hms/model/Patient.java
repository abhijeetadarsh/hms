package online.abhijeetadarsh.hms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import online.abhijeetadarsh.hms.common.User;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Patient extends User {
    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false, length = 10)
    private String gender;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(length = 5)
    private String bloodGroup;

    @Column(length = 100)
    private String emergencyContact;

    @Column(columnDefinition = "TEXT")
    private String allergies;

    @Column(columnDefinition = "TEXT")
    private String medicalHistory;

    public Patient() {
        super("patient_");
    }
}
