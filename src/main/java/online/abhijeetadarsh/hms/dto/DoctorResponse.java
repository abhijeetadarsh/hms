package online.abhijeetadarsh.hms.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DoctorResponse {
    String userId;
    String name;
    String email;
    String specialization;
    String department;
    String phoneNumber;
}