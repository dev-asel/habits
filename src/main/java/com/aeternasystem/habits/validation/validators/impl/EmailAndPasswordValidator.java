package com.aeternasystem.habits.validation.validators.impl;


import com.aeternasystem.habits.validation.validators.EmailAndPassword;
import com.aeternasystem.habits.web.dto.UserDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailAndPasswordValidator implements ConstraintValidator<EmailAndPassword, UserDTO> {

    @Override
    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext context) {
        if (userDTO == null) {
            return true;
        }

        boolean isEmailProvided = userDTO.getEmail() != null && !userDTO.getEmail().isEmpty();
        boolean isPasswordProvided = userDTO.getPassword() != null && !userDTO.getPassword().isEmpty();

        if ((isEmailProvided || isPasswordProvided) && !(isEmailProvided && isPasswordProvided)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "If email or password is provided, both must be present."
            ).addConstraintViolation();
            return false;
        }

        return true;
    }
}