package com.dataincloud.services.profile.dto.validator;

import com.dataincloud.core.exceptions.ResourceNotFoundException;
import com.dataincloud.services.user.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ExistingUserValidator implements ConstraintValidator<ExistingUser, Long> {
    @Autowired
    private UserService userService;

    @Override
    public void initialize(ExistingUser constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long userId, ConstraintValidatorContext constraintValidatorContext) {
        try {
            userService.getById(userId);
            return true;
        } catch (ResourceNotFoundException ex) {
            return false;
        }
    }
}
