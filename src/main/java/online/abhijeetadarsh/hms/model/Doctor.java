package online.abhijeetadarsh.hms.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Doctor extends User {
    private Long departmentId;
    private String specialization;
    private String qualification;
}
