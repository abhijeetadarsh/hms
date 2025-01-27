package online.abhijeetadarsh.hms.repository;

import online.abhijeetadarsh.hms.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {
    boolean existsByEmail(String email);

    Optional<Admin> findByEmail(String email);
}
