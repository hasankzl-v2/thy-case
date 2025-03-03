package com.example.thy.entity;

import com.example.thy.defination.CustomIntegerArrayType;
import com.example.thy.validator.ValidateOperationDays;
import com.example.thy.enums.TransportationTypeEnum;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Type;

import java.util.Arrays;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity

@Table(name = "transportation",
        uniqueConstraints = @UniqueConstraint(
                name = "unique_transportation",
                columnNames = {"origin_location_id", "destination_location_id", "transportation_type"}
        ))
@Check(constraints = "array_length(operation_days, 1) <= 7 AND operation_days <@ ARRAY[1,2,3,4,5,6,7]")
public class Transportation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "origin_location_id", nullable = false)
    private Location originLocation;


    @ManyToOne
    @JoinColumn(name = "destination_location_id", nullable = false)
    private Location destinationLocation;

    @Enumerated(EnumType.STRING)
    private TransportationTypeEnum transportationType;


    @Type(value = CustomIntegerArrayType.class)
    @Column(name = "operation_days", columnDefinition = "integer[]")
    @ValidateOperationDays(message = "operation days must contain unique values,values should be between 1-7 ,length should not be bigger that 7 ")
    private Integer[] operationDays;


    @Override
    public String toString() {
        return "Transportation{" +
                "transportationType=" + transportationType +
                ", operationDays=" + Arrays.toString(operationDays) +
                '}';
    }
}
