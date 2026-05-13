package com.healthtracker.view;

import com.healthtracker.auth.model.User;
import com.healthtracker.auth.service.UserService;
import com.healthtracker.meal.model.Meal;
import com.healthtracker.meal.service.MealService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
public class MealViewController {

    private final MealService mealService;
    private final UserService userService;

    public MealViewController(MealService mealService, UserService userService) {
        this.mealService = mealService;
        this.userService = userService;
    }

    @GetMapping("/meals/{userId}")
    public String mealsPage(@PathVariable Long userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        model.addAttribute("meals", mealService.getMealsByUserAndDate(userId, LocalDate.now()));
        model.addAttribute("newMeal", new Meal());
        return "meals";
    }

    @PostMapping("/meals/{userId}/add")
    public String addMeal(@PathVariable Long userId, @ModelAttribute Meal meal) {
        User user = userService.getUserById(userId);
        meal.setUser(user);
        meal.setDate(LocalDate.now());
        mealService.addMeal(meal);
        return "redirect:/meals/" + userId;
    }
}