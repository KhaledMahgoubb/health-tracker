package com.healthtracker.view;

import com.healthtracker.auth.model.User;
import com.healthtracker.auth.service.UserService;
import com.healthtracker.auth.model.UserProgress;
import com.healthtracker.auth.service.UserProgressService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProgressViewController {

    private final UserProgressService progressService;
    private final UserService userService;

    public ProgressViewController(UserProgressService progressService, UserService userService) {
        this.progressService = progressService;
        this.userService = userService;
    }

    @GetMapping("/user/progress")
    public String progressPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userService.getUserById(userId);
        UserProgress progress = progressService.getProgress(userId);
        model.addAttribute("user", user);
        model.addAttribute("progress", progress);
        return "progress";
    }

    @PostMapping("/user/progress/checkin")
    public String checkIn(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        try {
            progressService.checkIn(userId);
        } catch (RuntimeException e) {
            // already checked in today
        }
        return "redirect:/user/progress";
    }

    @PostMapping("/user/progress/update")
    public String weeklyUpdate(HttpSession session,
                               @RequestParam double newWeight,
                               @RequestParam String newActivityLevel) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        progressService.updateWeeklyInfo(userId, newWeight, newActivityLevel);
        return "redirect:/user/progress";
    }
}