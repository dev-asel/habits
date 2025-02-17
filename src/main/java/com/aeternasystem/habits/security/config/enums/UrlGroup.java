package com.aeternasystem.habits.security.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UrlGroup {
    PUBLIC_URLS(new String[]{
            "/css/**", "/js/**", "/images/**", "/fonts/**", "/images/logos/icon.ico",
            "/telegram/webhook", "/auth/telegram/callback",
            "/auth/app/callback/**", "/", "/login", "/error"
    }),
    USER_URLS(new String[]{
            "/habits", "/api/users/user/**"
    }),
    ADMIN_URLS(new String[]{
            "/users/**", "/api/users/**"
    }),
    ADMIN_GET_URLS(new String[]{
            "/api/logs", "api/habits"
    }),
    ADMIN_PUT_URLS(new String[]{
            "/api/logs/**", "/api/habits/**"
    }),
    AUTHORIZED_URLS(new String[]{
            "/api/logs/**", "/api/habits/**"
    });

    private final String[] urls;
}