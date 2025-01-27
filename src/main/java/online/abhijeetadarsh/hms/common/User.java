package online.abhijeetadarsh.hms.common;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class User {
    @Id
    private String userId;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean isActive;

    protected User(String tag) {
        this.userId = tag + java.util.UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.isActive = false;
    }

    public String getRole() {
        return this.getClass().getSimpleName().toUpperCase();
    }
}
