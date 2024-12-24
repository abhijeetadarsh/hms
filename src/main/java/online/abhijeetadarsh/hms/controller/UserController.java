package online.abhijeetadarsh.hms.controller;

import online.abhijeetadarsh.hms.model.User;
import online.abhijeetadarsh.hms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    UserService userService;

    @Autowired
    public UserController (UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        System.out.println(user);
        try {
            userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public void loginUser(@RequestParam String username, @RequestParam String password) {
        System.out.println(username + " " + password);
    }


//            getUserById
//    updateUser
//            deleteUser
    // Endpoint to fetch all users (for admin purposes)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
