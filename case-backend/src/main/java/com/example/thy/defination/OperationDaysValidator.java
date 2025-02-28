package com.example.thy.defination;

import com.example.thy.exception.TransportationOperationDaysNotValidException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

public class OperationDaysValidator implements ConstraintValidator<ValidateOperationDays, Integer[]> {

    @Override
    public boolean isValid(Integer[] values, ConstraintValidatorContext context) {
        // null acceptable
        if (values == null) return true;

        if (values.length > 7) {
            throw new TransportationOperationDaysNotValidException("Operation days array can have at most 7 elements.");
        }

        Set<Integer> uniqueValues = new HashSet<>();
        for (Integer value : values) {
            if (value < 1 || value > 7) {
               return false;
            }
            if (!uniqueValues.add(value)) {
               return false;
            }
        }
        return true;
    }


}