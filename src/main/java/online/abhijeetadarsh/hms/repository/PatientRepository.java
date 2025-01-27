package online.abhijeetadarsh.hms.repository;

import online.abhijeetadarsh.hms.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, String> {
    boolean existsByEmail(String email);

    Optional<Patient> findByEmail(String email);
}