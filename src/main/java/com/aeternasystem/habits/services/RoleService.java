package com.aeternasystem.habits.services;

import com.aeternasystem.habits.persistence.model.Role;
import com.aeternasystem.habits.util.enums.RoleName;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleService {
    Role save(Role role);
    Role create(Role role);

    Role update(Role role);
    Optional<Role> findById(Long id);
    Role getById(Long id);
    Optional<Role> findByName(RoleName roleName);
    Role getByName(RoleName roleName);
    Set<Role> getByIds(Set<Long> roleIds);
    List<Role> findAll();
    void createRoleIfNotExists(RoleName roleName);

    void delete(Role role);
}