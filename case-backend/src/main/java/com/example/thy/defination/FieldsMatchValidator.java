package com.example.thy.defination;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldsMatchValidator implements ConstraintValidator<FieldsMatch, Object> {

    private String firstField;
    private String secondField;
    private boolean shouldMatch;
    @Override
    public void initialize(FieldsMatch constraintAnnotation) {
        this.firstField = constraintAnnotation.firstField();
        this.secondField = constraintAnnotation.secondField();
        this.shouldMatch = constraintAnnotation.shouldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object firstValue = new BeanWrapperImpl(value).getPropertyValue(firstField);
        Object secondValue = new BeanWrapperImpl(value).getPropertyValue(secondField);
        return (firstValue == null && secondValue == null) ||
                (firstValue != null && shouldMatch == firstValue.equals(secondValue));
    }
}