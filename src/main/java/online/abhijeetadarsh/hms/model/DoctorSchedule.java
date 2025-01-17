package online.abhijeetadarsh.hms.model;

import lombok.Data;

import java.time.LocalTime;

@Data
public class DoctorSchedule {
    private Long scheduleId;
    private Long doctorUserId;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer maxAppointments;
    private boolean isAvailable;
}