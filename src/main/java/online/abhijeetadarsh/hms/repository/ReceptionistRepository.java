package online.abhijeetadarsh.hms.repository;

import online.abhijeetadarsh.hms.model.Receptionist;

import java.util.List;
import java.util.Optional;

public interface ReceptionistRepository {
    Receptionist findById(Long id);

    List<Receptionist> findAll();

    int save(Receptionist receptionist);

    int update(Receptionist receptionist);

    int delete(Long id);

    Optional<Receptionist> findByUsername(String username);

    Optional<Receptionist> findByEmail(String email);
}