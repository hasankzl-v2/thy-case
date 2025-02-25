package com.example.thy.entity;

import com.example.thy.enums.TransportationTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "transportation",
        uniqueConstraints = @UniqueConstraint(
                name = "unique_transportation",
                columnNames = {"origin_location_id", "destination_location_id", "transportation_type"}
        ))
public class Transportation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "origin_location_id",nullable = false)
    private Location originLocation;


    @ManyToOne
    @JoinColumn(name = "destination_location_id",nullable = false)
    private Location destinationLocation;

    @Enumerated(EnumType.STRING)
    private TransportationTypeEnum transportationType;
}
