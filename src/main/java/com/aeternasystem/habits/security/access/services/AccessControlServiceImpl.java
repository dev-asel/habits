package com.aeternasystem.habits.security.access.services;

import com.aeternasystem.habits.persistence.model.Habit;
import com.aeternasystem.habits.persistence.model.HabitLog;
import com.aeternasystem.habits.security.authentication.model.CustomUserDetails;
import com.aeternasystem.habits.services.HabitLogService;
import com.aeternasystem.habits.services.HabitService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccessControlServiceImpl implements AccessControlService {

    private final HabitService habitService;
    private final HabitLogService habitLogService;

    public AccessControlServiceImpl(HabitService habitService, HabitLogService habitLogService) {
        this.habitService = habitService;
        this.habitLogService = habitLogService;
    }

    @Override
    public void checkAccess(Long userId, Long currentUserId) {
        if (!userId.equals(currentUserId)) {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public void checkAccessByUserId(Long userId, CustomUserDetails currentUser) {
        checkAccess(userId, currentUser.getUserId());
    }

    @Override
    public void checkAccessByHabitId(Long habitId, CustomUserDetails currentUser) {
        Habit habit = habitService.getById(habitId);
        checkAccess(habit.getUser().getId(), currentUser.getUserId());
    }

    @Override
    public void checkAccessByLogId(Long logId, CustomUserDetails currentUser) {
        HabitLog habitLog = habitLogService.getById(logId);
        checkAccess(habitLog.getHabit().getUser().getId(), currentUser.getUserId());
    }
}