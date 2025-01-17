package online.abhijeetadarsh.hms.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class Patient extends User {
    private String name;
    private LocalDate dob;
    private String gender;
    private String address;
    private String phone;
    private String bloodGroup;
    private String emergencyContact;
    private String allergies;
    private String medicalHistory;
}
