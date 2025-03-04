package com.example.thy.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/*
* validates operation days
* Array must contain distinct values!
* Operation days array can have at most 7 elements
* array values should not be lower than 1 and bigger than 7
* * */
@Constraint(validatedBy = OperationDaysValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateOperationDays {
    String message() default "Array must contain distinct values! acceptable values [1-7]";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}