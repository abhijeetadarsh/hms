package online.abhijeetadarsh.hms.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MedicineInventory {
    private Long medicineId;
    private String name;
    private String type;
    private BigDecimal unitPrice;
    private Integer stockQuantity;
    private LocalDate expiryDate;
    private String manufacturer;
}