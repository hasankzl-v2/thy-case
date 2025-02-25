package com.example.thy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
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

    @OneToMany(mappedBy = "originLocation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transportation> originTransportations;

    @OneToMany(mappedBy = "destinationLocation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transportation> destinationTransportations;
}
