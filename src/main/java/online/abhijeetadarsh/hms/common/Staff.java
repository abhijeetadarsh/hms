package online.abhijeetadarsh.hms.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public class Staff extends User {
    @Column(name = "join_date", nullable = false)
    private LocalDate joinDate;

    @Column(nullable = false, length = 20)
    private String contact;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    protected Staff(String tag) {
        super(tag);
    }
}