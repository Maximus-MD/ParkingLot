package com.endava.md.internship.parkinglot.validation.annotation;

import com.endava.md.internship.parkinglot.validation.validator.AccessibleParkingLevelValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AccessibleParkingLevelValidator.class})
public @interface AccessibleParkingLevel {
    String message() default "{message.inaccessible-parking-level}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
