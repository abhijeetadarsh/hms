package online.abhijeetadarsh.hms.service.impl;

import online.abhijeetadarsh.hms.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import online.abhijeetadarsh.hms.model.User;
import online.abhijeetadarsh.hms.repository.UserRepository;
import online.abhijeetadarsh.hms.service.UserService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void updateUser(Long userId, User user) {
        User existingUser = getUserById(userId);
        existingUser.setEmail(user.getEmail());
        userRepository.save(existingUser);
    }

//    @Override
//    public User getCurrentUser() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        return userRepository.findByUsername(auth.getName())
//            .orElseThrow(() -> new UnauthorizedException("User not found"));
//    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.delete(userId);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return authorities;
    }
}