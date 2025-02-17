package com.aeternasystem.habits.config.initializer.services;

import com.aeternasystem.habits.config.ResourcesProperties;
import com.aeternasystem.habits.persistence.model.Role;
import com.aeternasystem.habits.services.RoleService;
import com.aeternasystem.habits.services.UserService;
import com.aeternasystem.habits.services.cache.RoleCache;
import com.aeternasystem.habits.util.enums.RoleName;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
@Transactional
public class DataInitializationServiceImpl implements InitializationService {

    private static final Logger logger = Logger.getLogger(DataInitializationServiceImpl.class.getName());

    private final UserService userService;
    private final RoleService roleService;
    private final RoleCache roleCache;

    private final String adminName;
    private final String adminUsername;
    private final String adminPassword;
    private final String testName;
    private final String testUsername;
    private final String testPassword;

    public DataInitializationServiceImpl(ResourcesProperties resourcesProperties, UserService userService, RoleService roleService, RoleCache roleCache) {
        this.userService = userService;
        this.roleService = roleService;
        this.adminName = resourcesProperties.getAdminName();
        this.adminUsername = resourcesProperties.getAdminUsername();
        this.adminPassword = resourcesProperties.getAdminPassword();
        this.testName = resourcesProperties.getTestName();
        this.testUsername = resourcesProperties.getTestUsername();
        this.testPassword = resourcesProperties.getTestPassword();
        this.roleCache = roleCache;
    }

    @Override
    public void initializeRoles() {
        List<RoleName> roleNames = Arrays.asList(
                RoleName.ROLE_ADMIN,
                RoleName.ROLE_USER,
                RoleName.ROLE_PREMIUM,
                RoleName.ROLE_BANNED
        );

        Set<Role> roles = new HashSet<>(roleService.findAll());
        roleCache.putRoles(roles);

        for (RoleName roleName : roleNames) {
            roleService.createRoleIfNotExists(roleName);
        }
    }

    @Override
    public void createAdminUser() {
        userService.register(adminUsername, adminPassword, adminName, RoleName.ROLE_ADMIN);
    }

    @Override
    public void createTestUser() {
        userService.register(testUsername, testPassword, testName, RoleName.ROLE_USER);
    }
}