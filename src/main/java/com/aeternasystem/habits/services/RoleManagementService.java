package com.aeternasystem.habits.services;

import com.aeternasystem.habits.persistence.model.Role;
import com.aeternasystem.habits.persistence.model.User;
import com.aeternasystem.habits.util.enums.RoleName;

import java.util.Set;

public interface RoleManagementService {
    boolean hasRole(User user, RoleName roleName);

    void assignRole(User user, RoleName roleName);

    void banUser(User user);

    void unbanUser(User user);

    void grantPremium(User user);

    void revokePremium(User user);

    void addRole(User user, RoleName roleName);

    void addRole(User user, Role role);

    void removeRole(User user, RoleName roleName);

    void removeRole(User user, Role role);

    void addRoles(User user, Set<Role> roles);

    void removeRoles(User user);

    void setRoles(User user, Set<Role> roles);

    void updateUserRole(User user, RoleName roleName, boolean add);

    void updateUserRole(User user, Role role, boolean add);
}