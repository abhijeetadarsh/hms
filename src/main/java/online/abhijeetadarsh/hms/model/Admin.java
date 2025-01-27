package online.abhijeetadarsh.hms.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import online.abhijeetadarsh.hms.common.User;

@Getter
@Setter
@Entity
public class Admin extends User {
    public Admin() {
        super("admin_");
    }
}
