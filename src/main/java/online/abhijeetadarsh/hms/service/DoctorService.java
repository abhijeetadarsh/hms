package online.abhijeetadarsh.hms.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import online.abhijeetadarsh.hms.dto.DoctorResponse;

public interface DoctorService {
    Page<DoctorResponse> getAllDoctors(Pageable pageable);
}
