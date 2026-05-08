package com.healthtracker.auth.controller;

import com.healthtracker.auth.model.UserProgress;
import com.healthtracker.auth.service.UserProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progress")
public class UserProgressController {

    @Autowired
    private UserProgressService progressService;

    // Frontend calls this to show day counter and unlock status
    @GetMapping("/{userId}")
    public ResponseEntity<UserProgress> getProgress(@PathVariable Long userId) {
        return ResponseEntity.ok(progressService.getProgress(userId));
    }

    // End-of-day button
    @PostMapping("/{userId}/checkin")
    public ResponseEntity<UserProgress> checkIn(@PathVariable Long userId) {
        return ResponseEntity.ok(progressService.checkIn(userId));
    }

    // Weekly update form submission
    @PostMapping("/{userId}/weekly-update")
    public ResponseEntity<UserProgress> weeklyUpdate(
            @PathVariable Long userId,
            @RequestParam double newWeight,
            @RequestParam String newActivityLevel) {
        return ResponseEntity.ok(progressService.updateWeeklyInfo(userId, newWeight, newActivityLevel));
    }

    // Lock weekly update manually if needed
    @PostMapping("/{userId}/lock")
    public ResponseEntity<Void> lockUpdate(@PathVariable Long userId) {
        progressService.lockWeeklyUpdate(userId);
        return ResponseEntity.ok().build();
    }
}
