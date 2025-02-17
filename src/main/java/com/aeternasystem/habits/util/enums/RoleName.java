package com.aeternasystem.habits.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleName {
    ROLE_USER("User"),
    ROLE_ADMIN("Admin"),
    ROLE_PREMIUM("Premium"),
    ROLE_BANNED("Banned");

    private final String displayName;
}