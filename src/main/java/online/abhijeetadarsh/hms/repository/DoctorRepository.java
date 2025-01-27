package online.abhijeetadarsh.hms.repository;

import online.abhijeetadarsh.hms.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, String> {
    boolean existsByEmail(String email);

    Optional<Doctor> findByEmail(String email);
}