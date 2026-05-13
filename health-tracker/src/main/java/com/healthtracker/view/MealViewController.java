package com.healthtracker.view;

import com.healthtracker.auth.model.User;
import com.healthtracker.auth.service.ActivityLogService;
import com.healthtracker.auth.service.UserService;
import com.healthtracker.meal.model.Meal;
import com.healthtracker.meal.service.MealService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
public class MealViewController {

	private final MealService mealService;
	private final UserService userService;
	private final ActivityLogService activityLogService;

	public MealViewController(MealService mealService, 
	                           UserService userService,
	                           ActivityLogService activityLogService) {
	    this.mealService = mealService;
	    this.userService = userService;
	    this.activityLogService = activityLogService;
	}

    @GetMapping("/user/meals")
    public String mealsPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userService.getUserById(userId);
        model.addAttribute("user", user);
        model.addAttribute("meals", mealService.getMealsByUserAndDate(userId, LocalDate.now()));
        model.addAttribute("newMeal", new Meal());
        return "meals";
    }

    @PostMapping("/user/meals/add")
    public String addMeal(HttpSession session, @ModelAttribute Meal meal) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userService.getUserById(userId);
        meal.setUser(user);
        meal.setDate(LocalDate.now());
        mealService.addMeal(meal);
        activityLogService.log(user, "Logged meal: " + meal.getName() + " (" + meal.getCalories() + " kcal)");
        return "redirect:/user/meals";
    }
}