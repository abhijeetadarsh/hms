package online.abhijeetadarsh.hms.repository;

import online.abhijeetadarsh.hms.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {
    Patient findById(Long id);

    List<Patient> findAll();

    int save(Patient patient);

    int update(Patient patient);

    int delete(Long id);

    Optional<Patient> findByUsername(String username);

    Optional<Patient> findByEmail(String email);
}