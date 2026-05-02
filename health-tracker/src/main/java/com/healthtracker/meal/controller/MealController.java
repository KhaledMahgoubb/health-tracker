package com.healthtracker.meal.controller;

import com.healthtracker.meal.model.Meal;
import com.healthtracker.meal.service.MealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @PostMapping
    public ResponseEntity<Meal> addMeal(@RequestBody Meal meal) {
        return ResponseEntity.ok(mealService.addMeal(meal));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Meal>> getMealsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(mealService.getMealsByUser(userId));
    }

    @GetMapping("/user/{userId}/by-date")
    public ResponseEntity<List<Meal>> getMealsByDate(
            @PathVariable Long userId,
            @RequestParam LocalDate date) {
        return ResponseEntity.ok(mealService.getMealsByUserAndDate(userId, date));
    }

    @GetMapping("/user/{userId}/calories")
    public ResponseEntity<Integer> getTotalCalories(
            @PathVariable Long userId,
            @RequestParam LocalDate date) {
        return ResponseEntity.ok(mealService.getTotalCalories(userId, date));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        mealService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }
}