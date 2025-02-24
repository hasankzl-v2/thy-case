package com.example.thy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "location")
public class Location extends BaseEntity {


    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String City;

    @Column(unique = true, nullable = false)
    private String LocationCode;

}
