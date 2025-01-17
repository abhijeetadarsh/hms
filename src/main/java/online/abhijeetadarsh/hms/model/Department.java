package online.abhijeetadarsh.hms.model;

import lombok.Data;

@Data
public class Department {
    private Long departmentId;
    private String name;
    private String description;
    private String location;
}