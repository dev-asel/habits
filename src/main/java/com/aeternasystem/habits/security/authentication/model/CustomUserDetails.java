package com.aeternasystem.habits.security.authentication.model;

import com.aeternasystem.habits.persistence.model.Role;
import com.aeternasystem.habits.persistence.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final String username;
    private final Set<Role> roles;
    private Long userId;
    private String name;
    private String password;

    public CustomUserDetails(String username, Set<Role> roles, Long userId, String name) {
        this.username = username;
        this.roles = roles;
        this.userId = userId;
        this.name = name;
    }

    public CustomUserDetails(User user) {
        this.username = user.getUsername();
        this.roles = user.getRoles();
        this.userId = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}