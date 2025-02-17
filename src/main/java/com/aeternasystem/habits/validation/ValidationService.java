package com.aeternasystem.habits.validation;

import com.aeternasystem.habits.exception.AuthenticationPrincipalNotFoundException;
import com.aeternasystem.habits.exception.InvalidIdException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class ValidationService {

    public void validatePathVariable(Long pathVariableId, Long idFromDTO) {
        if (!pathVariableId.equals(idFromDTO)) {
            throw new InvalidIdException(String.format("The provided id (%d) does not match the id in the DTO (%d).", pathVariableId, idFromDTO));
        }
    }

    public void validateAuthenticationPrincipal(UserDetails userDetails) {
        if (userDetails == null) {
            throw new AuthenticationPrincipalNotFoundException("AuthenticationPrincipal not found in Security Context");
        }
    }
}