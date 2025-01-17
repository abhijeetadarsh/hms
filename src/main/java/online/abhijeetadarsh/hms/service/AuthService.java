package online.abhijeetadarsh.hms.service;

import online.abhijeetadarsh.hms.dto.*;

public interface AuthService {
    MessageResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    void changePassword(ChangePasswordRequest request);
}