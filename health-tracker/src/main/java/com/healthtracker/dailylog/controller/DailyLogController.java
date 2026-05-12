package com.healthtracker.dailylog.controller;

import com.healthtracker.dailylog.model.DailyLog;
import com.healthtracker.dailylog.service.DailyLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controller mas2ol 3an API endpoints beta3 Daily Logging
@RestController
@RequestMapping("/api/logs")
public class DailyLogController {

    private final DailyLogService dailyLogService;

    // Constructor Injection lel service
    public DailyLogController(DailyLogService dailyLogService) {
        this.dailyLogService = dailyLogService;
    }

    // Endpoint 3shan user yelog meal
    // Example:
    // POST /api/logs/1/meal/2
    @PostMapping("/{userId}/meal/{mealId}")
    public ResponseEntity<DailyLog> logMeal(@PathVariable Long userId,
                                             @PathVariable Long mealId) {
        DailyLog log = dailyLogService.logMeal(userId, mealId);
        return ResponseEntity.ok(log);
    }

    // Endpoint 3shan ngeeb log beta3 el naharda
    // Example:
    // GET /api/logs/1/today
    @GetMapping("/{userId}/today")
    public ResponseEntity<DailyLog> getToday(@PathVariable Long userId) {
        DailyLog log = dailyLogService.getTodayLog(userId);
        return ResponseEntity.ok(log);
    }

    // Endpoint 3shan ngeeb history beta3 el user
    // Example:
    // GET /api/logs/1/history
    @GetMapping("/{userId}/history")
    public ResponseEntity<List<DailyLog>> getHistory(@PathVariable Long userId) {
        List<DailyLog> history = dailyLogService.getHistory(userId);
        return ResponseEntity.ok(history);
    }
}
