package com.aeternasystem.habits.services.impl;

import com.aeternasystem.habits.exception.RoleAlreadyExistException;
import com.aeternasystem.habits.exception.RoleNotFoundException;
import com.aeternasystem.habits.persistence.model.Role;
import com.aeternasystem.habits.persistence.repositories.RoleRepository;
import com.aeternasystem.habits.services.RoleService;
import com.aeternasystem.habits.services.cache.RoleCache;
import com.aeternasystem.habits.util.enums.RoleName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    private final RoleRepository roleRepository;
    private final RoleCache roleCache;

    public RoleServiceImpl(RoleRepository roleRepository, RoleCache roleCache) {
        this.roleRepository = roleRepository;
        this.roleCache = roleCache;
    }

    @Override
    public Role save(Role role) {
        Role savedRole = roleRepository.save(role);
        roleCache.put(savedRole);
        return savedRole;
    }

    @Override
    public Role create(Role role) {
        RoleName name = role.getName();
        if (findByName(name).isPresent()) {
            throw new RoleAlreadyExistException("Role with name " + name + " already exists");
        }
        Role createdRole = save(role);
        logger.info("Role with ID '{}' created", createdRole.getId());
        return createdRole;
    }

    @Override
    public Role update(Role role) {
        Role existingRole = getById(role.getId());
        existingRole.setName(role.getName());

        Role savedRole = save(existingRole);
        logger.info("Role with ID '{}' updated", savedRole.getId());
        return savedRole;
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleCache.findById(id)
                .or(() -> {
                    Optional<Role> role = roleRepository.findById(id);
                    role.ifPresent(roleCache::put);
                    return role;
                });
    }

    @Override
    public Role getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role with id " + id + " not found"));
    }

    @Override
    public Optional<Role> findByName(RoleName roleName) {
        return roleCache.findByName(roleName)
                .or(() -> {
                    Optional<Role> role = roleRepository.findByName(roleName);
                    role.ifPresent(roleCache::put);
                    return role;
                });
    }

    @Override
    public Role getByName(RoleName roleName) {
        return findByName(roleName)
                .orElseThrow(() -> new RoleNotFoundException("Role not found: " + roleName));
    }

    @Override
    public Set<Role> getByIds(Set<Long> roleIds) {
        return roleIds.stream()
                .map(this::getById)
                .collect(Collectors.toSet());
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void createRoleIfNotExists(RoleName roleName) {
        findByName(roleName).ifPresentOrElse(
                role -> logger.info("Role with ID '{}' already exists", role.getId()),
                () -> create(new Role(roleName)));
    }

    @Override
    public void delete(Role role) {
        roleCache.evict(role);
        roleRepository.delete(role);
        logger.info("Role with ID '{}' deleted", role.getId());
    }
}