package com.healthtracker.auth.controller;import com.healthtracker.auth.model.User;
import com.healthtracker.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User saved = userService.registerUser(user);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/bmi/{id}")
    public ResponseEntity<Double> getBMI(@PathVariable Long id) {
        // coming soon
        return ResponseEntity.ok(0.0);
    }
}