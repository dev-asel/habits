package com.aeternasystem.habits.config.initializer.services;

public interface InitializationService {
    void initializeRoles();
    void createAdminUser();
    void createTestUser();
}