package com.aeternasystem.habits.services.cache;

import java.util.Optional;

public abstract class BaseCache<T> {
    protected final CacheService cacheService;
    protected final String cacheName;

    protected BaseCache(CacheService cacheService, String cacheName) {
        this.cacheService = cacheService;
        this.cacheName = cacheName;
    }

    public Optional<T> findById(Long key, Class<T> type) {
        return getFromCache(key, type);
    }

    public Optional<T> getFromCache(Object key, Class<T> type) {
        return cacheService.get(cacheName, key, type);
    }

    public void putToCache(Object key, T value) {
        cacheService.put(cacheName, key, value);
    }

    public void evictCache(Object key) {
        cacheService.evict(cacheName, key);
    }
}