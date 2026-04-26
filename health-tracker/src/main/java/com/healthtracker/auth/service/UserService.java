package com.healthtracker.auth.service;

import com.healthtracker.auth.model.User;
import com.healthtracker.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
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
}