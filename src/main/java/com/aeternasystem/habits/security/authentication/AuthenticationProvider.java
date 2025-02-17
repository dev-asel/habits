package com.aeternasystem.habits.security.authentication;

import com.aeternasystem.habits.security.authentication.services.CustomUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider {
    private final CustomUserDetailsService customUserDetailsService;

    public AuthenticationProvider(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.getUserDetailsFromToken(token);
        return new AuthenticationToken(userDetails);
    }
}