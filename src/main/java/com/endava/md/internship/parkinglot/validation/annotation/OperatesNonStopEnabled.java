package com.endava.md.internship.parkinglot.validation.annotation;

import com.endava.md.internship.parkinglot.validation.validator.OperatesNonStopEnabledValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {OperatesNonStopEnabledValidator.class})
public @interface OperatesNonStopEnabled {

    String message() default "{message.parking-time-null-value}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
