package com.healthtracker.view;

import com.healthtracker.auth.model.User;
import com.healthtracker.auth.service.UserService;
import com.healthtracker.dailylog.model.DailyLog;
import com.healthtracker.dailylog.service.DailyLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardViewController {

    private final UserService userService;
    private final DailyLogService dailyLogService;

    public DashboardViewController(UserService userService, DailyLogService dailyLogService) {
        this.userService = userService;
        this.dailyLogService = dailyLogService;
    }

    @GetMapping("/user/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userService.getUserById(userId);
        double tdee = userService.calculateTDEE(user);
        double bmi = userService.calculateBMI(user);

        model.addAttribute("user", user);
        model.addAttribute("tdee", Math.round(tdee));
        model.addAttribute("bmi", String.format("%.1f", bmi));

        try {
            DailyLog log = dailyLogService.getTodayLog(userId);
            model.addAttribute("caloriesConsumed", log.getTotalCaloriesConsumed());
            model.addAttribute("meals", log.getMeals());
        } catch (RuntimeException e) {
            model.addAttribute("caloriesConsumed", 0);
            model.addAttribute("meals", null);
        }

        return "dashboard";
    }
}