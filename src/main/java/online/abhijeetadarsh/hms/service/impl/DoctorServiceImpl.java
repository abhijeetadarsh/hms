package online.abhijeetadarsh.hms.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import online.abhijeetadarsh.hms.dto.DoctorResponse;
import online.abhijeetadarsh.hms.model.Doctor;
import online.abhijeetadarsh.hms.repository.DoctorRepository;
import online.abhijeetadarsh.hms.service.DoctorService;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    @Override
    public Page<DoctorResponse> getAllDoctors(Pageable pageable) {
        Page<Doctor> doctors = doctorRepository.findByIsActiveTrue(pageable);
        return doctors.map(this::mapToResponse);
    }

    private DoctorResponse mapToResponse(Doctor doctor) {
        return DoctorResponse.builder()
                .userId(doctor.getUserId())
                .name(doctor.getName())
                .email(doctor.getEmail())
                .specialization(doctor.getSpecialization())
                .department(doctor.getDepartment().getName())
                .phoneNumber(doctor.getContact())
                .build();
    }
}