package com.healthtracker.dailylog.service;

import com.healthtracker.auth.model.User;
import com.healthtracker.auth.repository.UserRepository;
import com.healthtracker.dailylog.model.DailyLog;
import com.healthtracker.dailylog.repository.DailyLogRepository;
import com.healthtracker.meal.model.Meal;
import com.healthtracker.meal.repository.MealRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

// Service feha el business logic beta3 Daily Logging
@Service
public class DailyLogService {

    private final DailyLogRepository dailyLogRepository;
    private final UserRepository userRepository;
    private final MealRepository mealRepository;

    // Constructor Injection
    // Dah a7san w anadaf men @Autowired
    public DailyLogService(DailyLogRepository dailyLogRepository,
                           UserRepository userRepository,
                           MealRepository mealRepository) {
        this.dailyLogRepository = dailyLogRepository;
        this.userRepository = userRepository;
        this.mealRepository = mealRepository;
    }

    // Aham function fe part Khaled
    // Betkhali user yelog meal akalha el naharda
    public DailyLog logMeal(Long userId, Long mealId) {

        // Geb el user men database
        // 3amel cast 3shan UserRepository fe project raw type
        User user = (User) userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Geb el meal men database
        // 3amel cast 3shan MealRepository fe project raw type
        Meal meal = (Meal) mealRepository.findById(mealId)
            .orElseThrow(() -> new RuntimeException("Meal not found"));

        // Shoof el user 3ando log lel naharda wala la
        DailyLog log = dailyLogRepository
            .findByUserIdAndDate(userId, LocalDate.now())
            .orElseGet(() -> {

                // Law mafesh log lel naharda, e3mel log gedid
                DailyLog newLog = new DailyLog();
                newLog.setUser(user);
                newLog.setDate(LocalDate.now());
                newLog.setTotalCaloriesConsumed(0);
                return newLog;
            });

        // Add el meal lel daily log
        log.getMeals().add(meal);

        // Zawed calories beta3 el meal 3ala total calories
        log.setTotalCaloriesConsumed(
            log.getTotalCaloriesConsumed() + meal.getCalories()
        );

        // Save el daily log fe database
        return dailyLogRepository.save(log);
    }

    // Betgeb log beta3 el naharda lel user
    public DailyLog getTodayLog(Long userId) {
        return dailyLogRepository
            .findByUserIdAndDate(userId, LocalDate.now())
            .orElseThrow(() -> new RuntimeException("No log found for today"));
    }

    // Betgeb history beta3 kol logs lel user
    public List<DailyLog> getHistory(Long userId) {
        return dailyLogRepository.findByUserIdOrderByDateDesc(userId);
    }
}
