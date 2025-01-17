package online.abhijeetadarsh.hms.service;

import online.abhijeetadarsh.hms.model.User;

public interface JWTService {
    String generateToken(User user);
    boolean validateToken(String token);
    String getUserIdFromToken(String token);
}