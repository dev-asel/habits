package com.aeternasystem.habits.config.initializer;

import com.aeternasystem.habits.config.initializer.services.InitializationService;
import com.aeternasystem.habits.exception.DataInitializationException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class DataInitializationConfig {

    private static final Logger logger = Logger.getLogger(DataInitializationConfig.class.getName());

    private final InitializationService initializationService;

    public DataInitializationConfig(InitializationService initializationService) {
        this.initializationService = initializationService;
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            try {
                initializeRoles();
                initializeUsers();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Unexpected error during data initialization", e);
                throw new DataInitializationException("Unexpected error during data initialization");
            }
        };
    }

    private void initializeRoles() {
        try {
            initializationService.initializeRoles();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to create roles", e);
            throw new DataInitializationException("Failed to create roles");
        }
    }

    private void initializeUsers() {
        try {
            initializationService.createAdminUser();
            initializationService.createTestUser();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to create users", e);
            throw new DataInitializationException("Failed to create users");
        }
    }
}