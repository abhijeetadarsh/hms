package online.abhijeetadarsh.hms.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class Staff extends User {
    private String name;
    private LocalDate joinDate;
    private String contact;
    private String address;
}