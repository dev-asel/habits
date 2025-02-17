package com.aeternasystem.habits.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationToken extends AbstractAuthenticationToken {

    private final UserDetails userDetails;

    public AuthenticationToken(UserDetails userDetails) {
        super(userDetails.getAuthorities());
        this.userDetails = userDetails;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }
}