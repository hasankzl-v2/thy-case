package com.example.thy.defination;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // Class seviyesinde kullanacağız
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldsMatchValidator.class) // Validatör sınıfı
public @interface FieldsMatch {

    String message() default "Fields do not match"; // Hata mesajı

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String firstField();
    String secondField();
    boolean shouldMatch();
}