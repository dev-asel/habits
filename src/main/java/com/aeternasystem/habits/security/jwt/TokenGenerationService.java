package com.aeternasystem.habits.security.jwt;

import com.aeternasystem.habits.security.authentication.model.CustomUserDetails;
import com.aeternasystem.habits.util.web.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class TokenGenerationService {

    private final JwtTokenProvider jwtTokenProvider;

    public TokenGenerationService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void generateAndSetToken(HttpServletResponse response, CustomUserDetails userDetails) {
        String token = jwtTokenProvider.generateToken(userDetails);
        CookieUtil.addJwtCookie(response, token);
    }
}