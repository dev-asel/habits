package com.aeternasystem.habits.security.jwt;

import com.aeternasystem.habits.security.authentication.AuthenticationProvider;
import com.aeternasystem.habits.security.config.enums.UrlGroup;
import com.aeternasystem.habits.util.web.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger jwtLogger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final AuthenticationProvider authenticationProvider;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(AuthenticationProvider authenticationProvider, JwtTokenProvider jwtTokenProvider) {
        this.authenticationProvider = authenticationProvider;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private final RequestMatcher publicUrlsMatcher = new OrRequestMatcher(
            Arrays.stream(UrlGroup.PUBLIC_URLS.getUrls())
                    .map(AntPathRequestMatcher::new)
                    .collect(Collectors.toList())
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!publicUrlsMatcher.matches(request)) {
            String token = CookieUtil.resolveToken(request);

            if (token != null && jwtTokenProvider.validateToken(token)) {
                jwtLogger.info("JWT token is valid");
                Authentication authentication = authenticationProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                jwtLogger.info("Authentication successful for user: {}", authentication.getName());
            } else {
                jwtLogger.info("JWT token is invalid");
            }
        }
        filterChain.doFilter(request, response);
    }
}