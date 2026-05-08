package com.healthtracker.auth.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_progress")
public class UserProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int totalDaysCompleted;

    private LocalDate lastCheckIn;

    private boolean weeklyUpdateUnlocked;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public int getTotalDaysCompleted() { return totalDaysCompleted; }
    public void setTotalDaysCompleted(int totalDaysCompleted) { this.totalDaysCompleted = totalDaysCompleted; }

    public LocalDate getLastCheckIn() { return lastCheckIn; }
    public void setLastCheckIn(LocalDate lastCheckIn) { this.lastCheckIn = lastCheckIn; }

    public boolean isWeeklyUpdateUnlocked() { return weeklyUpdateUnlocked; }
    public void setWeeklyUpdateUnlocked(boolean weeklyUpdateUnlocked) { this.weeklyUpdateUnlocked = weeklyUpdateUnlocked; }
}
