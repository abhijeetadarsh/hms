package online.abhijeetadarsh.hms.repository;

import online.abhijeetadarsh.hms.model.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository {
    Doctor findById(Long id);

    List<Doctor> findAll();

    int save(Doctor doctor);

    int update(Doctor doctor);

    int delete(Long id);

    Optional<Doctor> findByUsername(String username);

    Optional<Doctor> findByEmail(String email);
}