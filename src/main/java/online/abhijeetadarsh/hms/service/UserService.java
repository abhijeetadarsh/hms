package online.abhijeetadarsh.hms.service;

import online.abhijeetadarsh.hms.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface UserService {
    User getUserById(Long userId);

    void updateUser(Long userId, User user);

    void deleteUser(Long userId);

    Collection<? extends GrantedAuthority> getAuthorities(User user);
}