package com.aeternasystem.habits.validation.validators;

import com.aeternasystem.habits.validation.validators.impl.EmailAndPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailAndPasswordValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailAndPassword {

    String message() default "If email or password is provided, both must be present.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}