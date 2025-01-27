package online.abhijeetadarsh.hms.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

/**
 * Service interface for JWT-related operations.
 */
public interface JWTService {

    public String extractUsername(String token);

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) ;

    public String generateToken(UserDetails userDetails) ;

    public boolean isTokenValid(String token, UserDetails userDetails) ;

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) ;
}