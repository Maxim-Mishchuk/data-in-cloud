package com.dataincloud.services.profile.dto.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistingUserValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistingUser {
    String message() default "This user does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
