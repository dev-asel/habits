package com.aeternasystem.habits.security.authentication.services;

import com.aeternasystem.habits.persistence.model.Role;
import com.aeternasystem.habits.persistence.model.User;
import com.aeternasystem.habits.persistence.repositories.UserRepository;
import com.aeternasystem.habits.security.authentication.model.CustomUserDetails;
import com.aeternasystem.habits.security.jwt.JwtTokenProvider;
import com.aeternasystem.habits.util.web.JsonUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public CustomUserDetailsService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByChatIdOrEmail(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new CustomUserDetails(user);
    }

    public UserDetails getUserDetailsFromToken(String token) {
        Claims claims = jwtTokenProvider.getAllClaimsFromToken(token);
        String username = claims.getSubject();
        Long userId = claims.get("userId", Long.class);
        Set<Role> roles = JsonUtil.jsonToRoles(claims.get("roles", String.class));
        String name = claims.get("name", String.class);
        return new CustomUserDetails(username, roles, userId, name);
    }
}