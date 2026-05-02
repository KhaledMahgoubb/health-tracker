package com.healthtracker.meal.repository;

import com.healthtracker.meal.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByUserId(Long userId);
    List<Meal> findByUserIdAndDate(Long userId, LocalDate date);
}