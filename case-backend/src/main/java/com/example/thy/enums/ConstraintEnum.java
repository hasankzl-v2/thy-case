package com.example.thy.enums;

import lombok.Getter;

@Getter
public enum ConstraintEnum {
    UNIQUE_TRANSPORTATION("unique_transportation"),
    TRANSPORTATION_OPERATION_DAYS_CHECK("transportation_operation_days_check");


    private final String value;

    ConstraintEnum(String value) {
        this.value = value;
    }

}
