package online.abhijeetadarsh.hms.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import online.abhijeetadarsh.hms.model.User;
import online.abhijeetadarsh.hms.service.JWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JWTServiceImpl implements JWTService {
    private final String SECRET_KEY;
    private final long EXPIRATION_TIME;

    public JWTServiceImpl(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration}") long expirationTime) {
        this.SECRET_KEY = secretKey;
        this.EXPIRATION_TIME = expirationTime;
    }

    @Override
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getUserId()))
                .claim("username", user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private Key getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
