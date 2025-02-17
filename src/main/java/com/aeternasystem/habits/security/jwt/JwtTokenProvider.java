package com.aeternasystem.habits.security.jwt;

import com.aeternasystem.habits.config.ResourcesProperties;
import com.aeternasystem.habits.security.authentication.model.CustomUserDetails;
import com.aeternasystem.habits.util.web.JsonUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private static final long JWT_EXPIRATION_MS = 3600000;
    private final SecretKey secretKey;

    public JwtTokenProvider(ResourcesProperties resourcesProperties) {
        this.secretKey = Keys.hmacShaKeyFor(resourcesProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("JWT token expired: {}", e.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Invalid JWT token", e);
        }
        return false;
    }

    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + JWT_EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", JsonUtil.rolesToJson(userDetails.getRoles()))
                .claim("userId", userDetails.getUserId())
                .claim("name", userDetails.getName())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.error("Could not extract claims from token", e);
            return Jwts.claims();
        }
    }
}