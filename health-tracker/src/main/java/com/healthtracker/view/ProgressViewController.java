package com.healthtracker.view;

import com.healthtracker.auth.model.User;
import com.healthtracker.auth.service.UserService;
import com.healthtracker.auth.model.UserProgress;
import com.healthtracker.auth.service.UserProgressService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProgressViewController {

    private final UserProgressService progressService;
    private final UserService userService;

    public ProgressViewController(UserProgressService progressService, UserService userService) {
        this.progressService = progressService;
        this.userService = userService;
    }

    @GetMapping("/progress/{userId}")
    public String progressPage(@PathVariable Long userId, Model model) {
        User user = userService.getUserById(userId);
        UserProgress progress = progressService.getProgress(userId);
        model.addAttribute("user", user);
        model.addAttribute("progress", progress);
        return "progress";
    }

    @PostMapping("/progress/{userId}/checkin")
    public String checkIn(@PathVariable Long userId, Model model) {
        try {
            progressService.checkIn(userId);
        } catch (RuntimeException e) {
            // already checked in today, just redirect back
        }
        return "redirect:/progress/" + userId;
    }

    @PostMapping("/progress/{userId}/update")
    public String weeklyUpdate(@PathVariable Long userId,
                               @RequestParam double newWeight,
                               @RequestParam String newActivityLevel) {
        progressService.updateWeeklyInfo(userId, newWeight, newActivityLevel);
        return "redirect:/progress/" + userId;
    }
}