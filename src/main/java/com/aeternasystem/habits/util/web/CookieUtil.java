package com.aeternasystem.habits.util.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;

public class CookieUtil {
    private static final String JWT_TOKEN = "JWT_TOKEN";
    private static final int COOKIE_MAX_AGE = 24 * 60 * 60;

    private CookieUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void addJwtCookie(HttpServletResponse response, String token) {
        Cookie jwtCookie = new Cookie(JWT_TOKEN, token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(COOKIE_MAX_AGE);
        response.addCookie(jwtCookie);
    }

    public static String resolveToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> JWT_TOKEN.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    public static void clearJwtCookie(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie(JWT_TOKEN, null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        response.addCookie(jwtCookie);
    }
}