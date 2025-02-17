package com.aeternasystem.habits.validation.validators;

import com.aeternasystem.habits.validation.validators.impl.EmailOrChatIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailOrChatIdValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailOrChatId {
    String message() default "Either email or chatId must be provided.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}