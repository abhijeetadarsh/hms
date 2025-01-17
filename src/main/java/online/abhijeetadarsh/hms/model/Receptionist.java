package online.abhijeetadarsh.hms.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Receptionist extends Staff {
    private String shift;
    private String deskLocation;
}