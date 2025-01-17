package online.abhijeetadarsh.hms.repository;

import online.abhijeetadarsh.hms.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    void save(User user);

    void update(User user);

    void delete(Long id);
}