package com.endava.md.internship.parkinglot.validation.annotation;

import com.endava.md.internship.parkinglot.validation.validator.EmptyWorkingDaysValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EmptyWorkingDaysValidator.class})
public @interface EmptyWorkingDays {
    String message() default "{message.empty-working-days}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
