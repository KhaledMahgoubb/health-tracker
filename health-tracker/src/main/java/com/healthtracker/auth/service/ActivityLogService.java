package com.healthtracker.auth.service;

import com.healthtracker.auth.model.ActivityLog;
import com.healthtracker.auth.model.User;
import com.healthtracker.auth.repository.ActivityLogRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    public ActivityLogService(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    public void log(User user, String action) {
        ActivityLog activityLog = new ActivityLog(user, action);
        activityLogRepository.save(activityLog);
    }

    public List<ActivityLog> getUserHistory(Long userId) {
        return activityLogRepository.findByUserIdOrderByTimestampDesc(userId);
    }

    public List<ActivityLog> getAllHistory() {
        return activityLogRepository.findAllByOrderByTimestampDesc();
    }
}