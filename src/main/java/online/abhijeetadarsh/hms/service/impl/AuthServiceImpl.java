package online.abhijeetadarsh.hms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import online.abhijeetadarsh.hms.dto.*;
import online.abhijeetadarsh.hms.exception.UnauthorizedException;
import online.abhijeetadarsh.hms.model.User;
import online.abhijeetadarsh.hms.repository.UserRepository;
import online.abhijeetadarsh.hms.service.AuthService;
import online.abhijeetadarsh.hms.service.JWTService;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    private JWTService jwtService;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(JWTService jwtService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public MessageResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        userRepository.save(user);
        //[TODO] handle exception

        return new MessageResponse("User registered successfully");
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);
        return new LoginResponse(token);
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new UnauthorizedException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.update(user);
    }
}
