package online.abhijeetadarsh.hms.service;

import online.abhijeetadarsh.hms.common.User;

import java.util.Optional;

/**
 * Service interface for user-related operations.
 */
public interface UserService {

    /**
     * Retrieves user details by email.
     *
     * @param email the email of the user
     * @return the user details
     */
    Optional<User> findByEmail(String email);

    /**
     * Retrieves user details by user ID.
     *
     * @param userId the ID of the user
     * @return the user details
     */
    Optional<User> findById(String userId);
}
