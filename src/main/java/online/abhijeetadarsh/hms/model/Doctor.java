package online.abhijeetadarsh.hms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import online.abhijeetadarsh.hms.common.Staff;

@Getter
@Setter
@Entity
public class Doctor extends Staff {
    @ManyToOne
    @JoinColumn(nullable = false)
    private Department department;

    @Column(nullable = false, length = 100)
    private String specialization;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String qualification;

    public Doctor() {
        super("doctor_");
    }
}
