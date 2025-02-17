package com.aeternasystem.habits.services.cache;

import com.aeternasystem.habits.persistence.model.User;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;


@Service
public class UserCache extends BaseCache<User> {
    public UserCache(CacheService cacheService) {
        super(cacheService, "users");
    }

    public Optional<User> findById(Long key) {
        return getFromCache(key, User.class);
    }

    public Optional<User> findByUsername(String chatId, String email) {
        return findByKey(chatId).or(() -> findByKey(email));
    }

    public Optional<User> findByKey(String key) {
        return getFromCache(key, User.class);
    }

    public void put(User user) {
        Stream.of(user.getId(), user.getChatId(), user.getUsername(), user.getEmail())
                .filter(Objects::nonNull)
                .forEach(key -> putToCache(key, user));
    }

    public void evict(User user) {
        Stream.of(user.getId(), user.getChatId(), user.getUsername(), user.getEmail())
                .filter(Objects::nonNull)
                .forEach(this::evictCache);
    }
}