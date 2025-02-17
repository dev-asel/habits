package com.aeternasystem.habits.validation;

import com.aeternasystem.habits.security.authentication.model.CustomUserDetails;
import com.aeternasystem.habits.web.dto.HabitDTO;
import com.aeternasystem.habits.web.dto.HabitLogDTO;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class ValidationAspect {

    private final ValidationService validationService;

    public ValidationAspect(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Before(value = "(execution(* com.aeternasystem.habits.web.controllers.HabitLogRestController.update(..)) || " +
            "execution(* com.aeternasystem.habits.web.controllers.HabitLogRestController.updateDate(..))) && args(id, habitLogDTO)", argNames = "id,habitLogDTO")
    public void validateHabitLogUpdatePathVariable(Long id, HabitLogDTO habitLogDTO) {
        validationService.validatePathVariable(id, habitLogDTO.getId());
    }

    @Before(value = "(execution(* com.aeternasystem.habits.web.controllers.HabitRestController.update(..)) || " +
            "execution(* com.aeternasystem.habits.web.controllers.HabitRestController.updateName(..))) && args(id, habitDTO)", argNames = "id,habitDTO")
    public void validateHabitUpdatePathVariable(Long id, HabitDTO habitDTO) {
        validationService.validatePathVariable(id, habitDTO.getId());
    }

    @Before("execution(* com.aeternasystem.habits.web.controllers.*.*(..)) && args(userDetails,..)")
    public void validateAuthenticationPrincipal(CustomUserDetails userDetails) {
        validationService.validateAuthenticationPrincipal(userDetails);
    }
}