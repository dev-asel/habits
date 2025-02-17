package com.aeternasystem.habits.services.cache;

import com.aeternasystem.habits.persistence.model.Habit;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class HabitCache extends BaseCache<Habit> {
    public HabitCache(CacheService cacheService) {
        super(cacheService, "habits");
    }

    public Optional<Habit> findById(Long key) {
        return getFromCache(key, Habit.class);
    }

    public Optional<Habit> findByKey(String key) {
        return getFromCache(key, Habit.class);
    }

    public void put(Habit habit) {
        putToCache(habit.getId(), habit);
    }

    public void evict(Habit habit) {
        evictCache(habit.getId());
    }
}