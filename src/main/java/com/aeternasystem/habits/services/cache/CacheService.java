package com.aeternasystem.habits.services.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CacheService {
    private final CacheManager cacheManager;

    public CacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public <T> Optional<T> get(String cacheName, Object key, Class<T> type) {
        Cache cache = cacheManager.getCache(cacheName);
        return Optional.ofNullable(cache != null ? cache.get(key, type) : null);
    }

    public void put(String cacheName, Object key, Object value) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, value);
        }
    }

    public void evict(String cacheName, Object key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
        }
    }

    public void clear(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }
}