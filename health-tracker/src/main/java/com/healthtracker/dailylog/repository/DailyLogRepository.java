package com.healthtracker.dailylog.repository;

import com.healthtracker.dailylog.model.DailyLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Repository mas2ol 3an database queries beta3 daily logs
@Repository
public interface DailyLogRepository extends JpaRepository<DailyLog, Long> {

    // Betgeb log beta3 user mo3ayan fe date mo3ayan
    // Hanestakhdemha 3shan ngeeb log bta3 el naharda
    Optional<DailyLog> findByUserIdAndDate(Long userId, LocalDate date);

    // Betgeb kol logs beta3 user men el latest lel oldest
    List<DailyLog> findByUserIdOrderByDateDesc(Long userId);
}
