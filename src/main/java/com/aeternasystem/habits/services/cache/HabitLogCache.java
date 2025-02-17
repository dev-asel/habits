package com.aeternasystem.habits.services.cache;

import com.aeternasystem.habits.persistence.model.HabitLog;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HabitLogCache extends BaseCache<HabitLog> {
    public HabitLogCache(CacheService cacheService) {
        super(cacheService, "habitlogs");
    }

    public Optional<HabitLog> findById(Long key) {
        return getFromCache(key, HabitLog.class);
    }

    public Optional<HabitLog> findByKey(String key) {
        return getFromCache(key, HabitLog.class);
    }

    public void put(HabitLog habitLog) {
        putToCache(habitLog.getId(), habitLog);
    }

    public void evict(HabitLog habitLog) {
        evictCache(habitLog.getId());
    }
}
