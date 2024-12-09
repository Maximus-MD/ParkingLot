package com.endava.md.internship.parkinglot.validation.annotation;

import com.endava.md.internship.parkinglot.validation.validator.AccessibleParkingSpotValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AccessibleParkingSpotValidator.class})
public @interface AccessibleParkingSpot {
    String message() default "{message.inaccessible-parking-spot}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
