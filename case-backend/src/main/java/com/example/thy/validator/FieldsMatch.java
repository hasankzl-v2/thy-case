package com.example.thy.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
* FieldsMatch validator can be use to validate 2 different data should be same or not.
* if shouldMatch is true fistField and secondField should match otherwise throws error
* */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldsMatchValidator.class) // Validator class
public @interface FieldsMatch {
    // Error message
    String message() default "field matcher error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String firstField();
    String secondField();
    boolean shouldMatch();
}