package com.healthtracker.auth.service;

import com.healthtracker.auth.model.User;
import com.healthtracker.auth.repository.UserProgressRepository;
import com.healthtracker.auth.repository.UserRepository;
import com.healthtracker.auth.model.UserProgress;
import java.util.Optional;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProgressService progressService;
    private final UserProgressRepository progressRepository;

    public UserService(UserRepository userRepository,UserProgressService progressService, UserProgressRepository progressRepository) {
        this.userRepository = userRepository;
        this.progressService = progressService;
        this.progressRepository = progressRepository;
    }

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        user.setRole("USER"); // default role
        User saved = userRepository.save(user);
        progressService.initProgress(saved);
        return saved;
    }

    public double calculateBMI(User user) {
        double heightInMeters = user.getHeight() / 100;
        return user.getWeight() / (heightInMeters * heightInMeters);
    }

    public double calculateBMR(User user) {
        if (user.getGender().equalsIgnoreCase("male")) {
            return 88.36 + (13.4 * user.getWeight()) 
                         + (4.8 * user.getHeight()) 
                         - (5.7 * user.getAge());
        } else {
            return 447.6 + (9.2 * user.getWeight()) 
                         + (3.1 * user.getHeight()) 
                         - (4.3 * user.getAge());
        }
    }

    public double calculateTDEE(User user) {
        double bmr = calculateBMR(user);
        switch (user.getActivityLevel()) {
            case "SEDENTARY": return bmr * 1.2;
            case "LIGHT":     return bmr * 1.375;
            case "MODERATE":  return bmr * 1.55;
            case "ACTIVE":    return bmr * 1.725;
            default:          return bmr;
        }
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        // delete progress first
        Optional<UserProgress> progress = progressRepository.findByUserId(id);
        progress.ifPresent(p -> progressRepository.delete(p));
        
        // delete the user
        userRepository.deleteById(id);
    }
}