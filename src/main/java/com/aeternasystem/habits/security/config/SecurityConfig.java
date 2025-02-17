package com.aeternasystem.habits.security.config;

import com.aeternasystem.habits.security.config.enums.UrlGroup;
import com.aeternasystem.habits.security.handlers.CustomAuthenticationSuccessHandler;
import com.aeternasystem.habits.security.handlers.CustomLogoutSuccessHandler;
import com.aeternasystem.habits.security.jwt.JwtAuthenticationFilter;
import com.aeternasystem.habits.security.jwt.TokenGenerationService;
import com.aeternasystem.habits.util.enums.RoleName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final TokenGenerationService tokenGenerationService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, TokenGenerationService tokenGenerationService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.tokenGenerationService = tokenGenerationService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler(tokenGenerationService);
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(UrlGroup.PUBLIC_URLS.getUrls()).permitAll()
                        .requestMatchers(HttpMethod.GET, UrlGroup.ADMIN_GET_URLS.getUrls()).hasAuthority(RoleName.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, UrlGroup.ADMIN_GET_URLS.getUrls()).hasAuthority(RoleName.ROLE_ADMIN.name())
                        .requestMatchers(UrlGroup.USER_URLS.getUrls()).hasAuthority(RoleName.ROLE_USER.name())
                        .requestMatchers(UrlGroup.ADMIN_URLS.getUrls()).hasAuthority(RoleName.ROLE_ADMIN.name())
                        .requestMatchers(UrlGroup.AUTHORIZED_URLS.getUrls()).hasAnyAuthority(RoleName.ROLE_USER.name(), RoleName.ROLE_ADMIN.name())
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex.accessDeniedPage("/access-denied"))
                .formLogin(form -> form
                        .loginPage("/")
                        .loginProcessingUrl("/")
                        .failureUrl("/?error=true")
                        .successHandler(authenticationSuccessHandler())
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .logoutSuccessHandler(logoutSuccessHandler())
                        .deleteCookies("JWT_TOKEN")
                        .permitAll()
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
