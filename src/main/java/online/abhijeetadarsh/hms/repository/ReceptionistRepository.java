package online.abhijeetadarsh.hms.repository;

import online.abhijeetadarsh.hms.model.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceptionistRepository extends JpaRepository<Receptionist, String> {
    boolean existsByEmail(String email);

    Optional<Receptionist> findByEmail(String email);
}