package com.aeternasystem.habits.validation.validators.impl;

import com.aeternasystem.habits.validation.validators.EmailOrChatId;
import com.aeternasystem.habits.web.dto.UserDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailOrChatIdValidator implements ConstraintValidator<EmailOrChatId, UserDTO> {

    @Override
    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext context) {
        return userDTO.getEmail() != null && !userDTO.getEmail().isEmpty() ||
                userDTO.getChatId() != null && !userDTO.getChatId().isEmpty();
    }
}