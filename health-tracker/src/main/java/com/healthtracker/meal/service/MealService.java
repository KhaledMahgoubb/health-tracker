package com.healthtracker.meal.service;

import com.healthtracker.meal.model.Meal;
import com.healthtracker.meal.repository.MealRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealService {

    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public Meal addMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    public List<Meal> getMealsByUser(Long userId) {
        return mealRepository.findByUserId(userId);
    }

    public List<Meal> getMealsByUserAndDate(Long userId, LocalDate date) {
        return mealRepository.findByUserIdAndDate(userId, date);
    }

    public int getTotalCalories(Long userId, LocalDate date) {
        return mealRepository.findByUserIdAndDate(userId, date)
                .stream()
                .mapToInt(Meal::getCalories)
                .sum();
    }

    public void deleteMeal(Long id) {
        mealRepository.deleteById(id);
    }
}