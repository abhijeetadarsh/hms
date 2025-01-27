package online.abhijeetadarsh.hms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import online.abhijeetadarsh.hms.common.Staff;

@Getter
@Setter
@Entity
public class Receptionist extends Staff {
    @Column(nullable = false, length = 20)
    private String shift;

    @Column(length = 50)
    private String deskLocation;

    public Receptionist() {
        super("receptionist_");
    }
}