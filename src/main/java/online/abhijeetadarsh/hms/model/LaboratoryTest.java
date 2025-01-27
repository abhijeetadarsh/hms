package online.abhijeetadarsh.hms.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class LaboratoryTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Appointment appointment;

    @Column(nullable = false, length = 100)
    private String testName;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal testCost;

    @Column(nullable = false)
    private LocalDateTime testTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TestStatus testStatus;

    @Column(columnDefinition = "TEXT")
    private String result;

    @Column(columnDefinition = "TEXT")
    private String normalRange;

    public enum TestStatus {
        Pending,
        InProgress,
        Completed,
        Cancelled
    }
}

