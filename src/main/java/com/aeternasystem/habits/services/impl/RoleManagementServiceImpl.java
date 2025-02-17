package com.aeternasystem.habits.services.impl;

import com.aeternasystem.habits.persistence.model.Role;
import com.aeternasystem.habits.persistence.model.User;
import com.aeternasystem.habits.services.RoleManagementService;
import com.aeternasystem.habits.services.RoleService;
import com.aeternasystem.habits.util.enums.RoleName;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleManagementServiceImpl implements RoleManagementService {

    private final RoleService roleService;

    public RoleManagementServiceImpl(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public boolean hasRole(User user, RoleName roleName) {
        return user.getRoles().stream().anyMatch(role -> role.getName().equals(roleName));
    }

    @Override
    public void assignRole(User user, RoleName roleName) {
        removeRoles(user);
        addRole(user, roleName);
    }

    @Override
    public void banUser(User user) {
        addRole(user, RoleName.ROLE_BANNED);
    }

    @Override
    public void unbanUser(User user) {
        removeRole(user, RoleName.ROLE_BANNED);
    }

    @Override
    public void grantPremium(User user) {
        addRole(user, RoleName.ROLE_PREMIUM);
    }

    @Override
    public void revokePremium(User user) {
        removeRole(user, RoleName.ROLE_PREMIUM);
    }

    @Override
    public void addRole(User user, RoleName roleName) {
        updateUserRole(user, roleName, true);
    }

    @Override
    public void addRole(User user, Role role) {
        updateUserRole(user, role, true);
    }

    @Override
    public void removeRole(User user, RoleName roleName) {
        updateUserRole(user, roleName, false);
    }

    @Override
    public void removeRole(User user, Role role) {
        updateUserRole(user, role, false);
    }

    @Override
    public void addRoles(User user, Set<Role> roles) {
        user.getRoles().addAll(roles);
    }

    @Override
    public void removeRoles(User user) {
        user.getRoles().clear();
    }

    @Override
    public void setRoles(User user, Set<Role> roles) {
        removeRoles(user);
        addRoles(user, roles);
    }

    @Override
    public void updateUserRole(User user, RoleName roleName, boolean add) {
        Role role = roleService.getByName(roleName);
        updateUserRole(user, role, add);
    }

    @Override
    public void updateUserRole(User user, Role role, boolean add) {
        if (add) {
            user.getRoles().add(role);
        } else {
            user.getRoles().remove(role);
        }
    }
}