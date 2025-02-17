package com.aeternasystem.habits.util;

import com.aeternasystem.habits.persistence.model.Role;
import com.aeternasystem.habits.util.enums.RoleName;

import java.util.Set;

public class RoleUtil {

    private RoleUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static boolean hasRole(Set<Role> roles, RoleName roleName) {
        return roles.stream().anyMatch(role -> role.getName().equals(roleName));
    }

    public static boolean isAdmin(Set<Role> roles) {
        return hasRole(roles, RoleName.ROLE_ADMIN);
    }
}