package online.abhijeetadarsh.hms.repository;

import online.abhijeetadarsh.hms.model.Staff;

import java.util.List;
import java.util.Optional;

public interface StaffRepository {
    Staff findById(Long id);

    List<Staff> findAll();

    int save(Staff staff);

    int update(Staff staff);

    int delete(Long id);

    Optional<Staff> findByUsername(String username);

    Optional<Staff> findByEmail(String email);
}
