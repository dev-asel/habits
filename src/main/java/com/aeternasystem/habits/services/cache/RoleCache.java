package com.aeternasystem.habits.services.cache;

import com.aeternasystem.habits.persistence.model.Role;
import com.aeternasystem.habits.util.enums.RoleName;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class RoleCache extends BaseCache<Role> {
    public RoleCache(CacheService cacheService) {
        super(cacheService, "roles");
    }

    public Optional<Role> findById(Long key) {
        return getFromCache(key, Role.class);
    }

    public Optional<Role> findByName(RoleName roleName) {
        return getFromCache(roleName, Role.class);
    }

    public Optional<Role> findByKey(String key) {
        return getFromCache(key, Role.class);
    }

    public void put(Role role) {
        putToCache(role.getId(), role);
        putToCache(role.getName(), role);
    }

    public void evict(Role role) {
        evictCache(role.getId());
        evictCache(role.getName());
    }

    public void putRoles(Set<Role> roles) {
        roles.forEach(this::put);
    }
}