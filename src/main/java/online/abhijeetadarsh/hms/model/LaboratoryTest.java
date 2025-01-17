package online.abhijeetadarsh.hms.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LaboratoryTest {
    private Long testId;
    private Long appointmentId;
    private String testName;
    private Double testCost;
    private LocalDateTime testTime;
    private String testStatus;
    private String result;
    private String normalRange;
}