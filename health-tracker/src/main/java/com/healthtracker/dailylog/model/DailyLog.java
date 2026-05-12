package com.healthtracker.dailylog.model;

import com.healthtracker.auth.model.User;
import com.healthtracker.meal.model.Meal;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Dah el model / entity beta3 Daily Logging
// Hayetkhazen fe database table esmo daily_logs
@Entity
@Table(name = "daily_logs")
public class DailyLog {

    // ID unique lel daily log
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Kol daily log belong to one user
    // User wahed momken yeb2a 3ando daily logs kteer
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Date beta3 el log, masalan log bta3 el naharda
    private LocalDate date;

    // Magmoo3 calories ely el user akalha fe el yom dah
    private double totalCaloriesConsumed;

    // Daily log wahed momken yeb2a feeh meals kteer
    // W meal wahed momken yetkarar fe logs mokhtalefa
    @ManyToMany
    @JoinTable(
        name = "daily_log_meals",
        joinColumns = @JoinColumn(name = "daily_log_id"),
        inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private List<Meal> meals = new ArrayList<>();

    // Getters and Setters 3shan JPA w Spring yestakhdem el fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTotalCaloriesConsumed() {
        return totalCaloriesConsumed;
    }

    public void setTotalCaloriesConsumed(double totalCaloriesConsumed) {
        this.totalCaloriesConsumed = totalCaloriesConsumed;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
