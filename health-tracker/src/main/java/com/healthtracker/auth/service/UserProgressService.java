package com.healthtracker.auth.service;

import com.healthtracker.auth.model.User;
import com.healthtracker.auth.model.UserProgress;
import com.healthtracker.auth.repository.UserProgressRepository;
import com.healthtracker.auth.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class UserProgressService {

    @Autowired
    private UserProgressRepository progressRepo;
    private UserRepository userRepository;


    // Called when user presses the end-of-day button
    public UserProgress checkIn(Long userId) {
        UserProgress progress = progressRepo.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("User progress not found"));

        LocalDate today = LocalDate.now();

        // Block double check-in on same day
        if (today.equals(progress.getLastCheckIn())) {
            throw new RuntimeException("Already checked in today!");
        }

        progress.setLastCheckIn(today);
        progress.setTotalDaysCompleted(progress.getTotalDaysCompleted() + 1);

        // Every 7 days unlock the weekly update form
        if (progress.getTotalDaysCompleted() % 7 == 0) {
            progress.setWeeklyUpdateUnlocked(true);
        }

        return progressRepo.save(progress);
    }

    // Called after user submits their weekly info update
    public void lockWeeklyUpdate(Long userId) {
        UserProgress progress = progressRepo.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("User progress not found"));

        progress.setWeeklyUpdateUnlocked(false);
        progressRepo.save(progress);
    }

    // Called when a new user registers
    public UserProgress initProgress(User user) {
        UserProgress progress = new UserProgress();
        progress.setUser(user);
        progress.setTotalDaysCompleted(0);
        progress.setWeeklyUpdateUnlocked(false);
        return progressRepo.save(progress);
    }

    // Called to display the counter and unlock status
    public UserProgress getProgress(Long userId) {
        return progressRepo.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("User progress not found"));
    }

    // Called when user submits weekly update form
    public UserProgress updateWeeklyInfo(Long userId, double newWeight, String newActivityLevel) {
        UserProgress progress = progressRepo.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("User progress not found"));

        if (!progress.isWeeklyUpdateUnlocked()) {
            throw new RuntimeException("Weekly update is not unlocked yet!");
        }

        // Update user's weight and activity level
        User user = progress.getUser();
        user.setWeight(newWeight);
        user.setActivityLevel(newActivityLevel);
        userRepository.save(user);


        // Lock again after update
        progress.setWeeklyUpdateUnlocked(false);

        return progressRepo.save(progress);
    }
}
