package com.aeternasystem.habits.security.access.services;

import com.aeternasystem.habits.security.authentication.model.CustomUserDetails;

public interface AccessControlService {
    void checkAccess(Long userId, Long currentUserId);

    void checkAccessByUserId(Long userId, CustomUserDetails currentUser);

    void checkAccessByHabitId(Long habitId, CustomUserDetails currentUser);

    void checkAccessByLogId(Long logId, CustomUserDetails currentUser);
}