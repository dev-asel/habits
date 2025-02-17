package com.aeternasystem.habits.security.access;

import com.aeternasystem.habits.security.access.services.AccessControlService;
import com.aeternasystem.habits.security.authentication.model.CustomUserDetails;
import com.aeternasystem.habits.util.RoleUtil;
import com.aeternasystem.habits.web.dto.HabitDTO;
import com.aeternasystem.habits.web.dto.HabitLogDTO;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Aspect
@Component
@Order(2)
public class AccessControlAspect {

    private final AccessControlService accessControlService;

    public AccessControlAspect(AccessControlService accessControlService) {
        this.accessControlService = accessControlService;
    }

    @Before("execution(* com.aeternasystem.habits.web.controllers.HabitRestController.getHabitsByUserId(..)) && args(userId)")
    public void checkHabitAccessByUserId(Long userId) {
        performAccessCheck((currentUser) -> accessControlService.checkAccessByUserId(userId, currentUser));
    }

    @Before("(execution(* com.aeternasystem.habits.web.controllers.HabitRestController.getHabitById(..)) || " +
            "execution(* com.aeternasystem.habits.web.controllers.HabitRestController.delete(..))) && args(id)")
    public void checkHabitAccessByHabitId(Long id) {
        performAccessCheck((currentUser) -> accessControlService.checkAccessByHabitId(id, currentUser));
    }

    @Before("execution(* com.aeternasystem.habits.web.controllers.HabitRestController.create(..)) && args(habitDTO)")
    public void checkCreateHabitAccessByHabitDTO(HabitDTO habitDTO) {
        Long userId = habitDTO.getUserId();
        performAccessCheck((currentUser) -> accessControlService.checkAccessByUserId(userId, currentUser));
    }

    @Before(value = "execution(* com.aeternasystem.habits.web.controllers.HabitRestController.updateName(..)) && args(id, habitDTO)", argNames = "id,habitDTO")
    public void checkUpdateNameHabitAccess(Long id, HabitDTO habitDTO) {
        performAccessCheck((currentUser) -> accessControlService.checkAccessByHabitId(id, currentUser));
    }

    @Before("(execution(* com.aeternasystem.habits.web.controllers.HabitLogRestController.getById(..)) || " +
            "execution(* com.aeternasystem.habits.web.controllers.HabitLogRestController.delete(..))) && args(id)")
    public void checkHabitLogAccessByHabitLogId(Long id) {
        performAccessCheck((currentUser) -> accessControlService.checkAccessByLogId(id, currentUser));
    }

    @Before("execution(* com.aeternasystem.habits.web.controllers.HabitLogRestController.getByHabitId*(..)) && args(habitId, ..)")
    public void checkHabitLogAccessByHabitId(Long habitId) {
        performAccessCheck(currentUser -> accessControlService.checkAccessByHabitId(habitId, currentUser));
    }

    @Before("execution(* com.aeternasystem.habits.web.controllers.HabitLogRestController.*(..)) && args(habitLogDTO)")
    public void checkHabitLogAccessByHabitLogDTO(HabitLogDTO habitLogDTO) {
        performAccessCheck((currentUser) -> accessControlService.checkAccessByHabitId(habitLogDTO.getHabitId(), currentUser));
    }

    @Before(value = "(execution(* com.aeternasystem.habits.web.controllers.HabitLogRestController.create(..)) || " +
            "execution(* com.aeternasystem.habits.web.controllers.HabitLogRestController.deleteByHabitIdAndDate(..))) && args(habitId, habitLogDTO)", argNames = "habitId,habitLogDTO")
    public void checkHabitLogAccess(Long habitId, HabitLogDTO habitLogDTO) {
        performAccessCheck((currentUser) -> accessControlService.checkAccessByHabitId(habitId, currentUser));
    }

    @Before(value = "execution(* com.aeternasystem.habits.web.controllers.HabitLogRestController.updateDate(..)) && args(id, habitLogDTO)", argNames = "id,habitLogDTO")
    public void checkUpdateDateHabitLogAccess(Long id, HabitLogDTO habitLogDTO) {
        performAccessCheck((currentUser) -> accessControlService.checkAccessByHabitId(id, currentUser));
    }

    private void performAccessCheck(Consumer<CustomUserDetails> accessCheck) {
        CustomUserDetails currentUser = getCurrentAuthenticatedUser();
        if (RoleUtil.isAdmin(currentUser.getRoles())) {
            return;
        }
        accessCheck.accept(currentUser);
    }

    private CustomUserDetails getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new AccessDeniedException("User is not authenticated");
        }
        return (CustomUserDetails) authentication.getPrincipal();
    }
}