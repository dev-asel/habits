package com.aeternasystem.habits.security.handlers;


import com.aeternasystem.habits.security.authentication.model.CustomUserDetails;
import com.aeternasystem.habits.security.jwt.TokenGenerationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenGenerationService tokenGenerationService;

    public CustomAuthenticationSuccessHandler(TokenGenerationService tokenGenerationService) {
        this.tokenGenerationService = tokenGenerationService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        tokenGenerationService.generateAndSetToken(response, userDetails);

        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setHeader("Location", "/loginSuccess");
    }
}