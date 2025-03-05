package com.example.thy.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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
    @NotBlank(message = "name should not be empty")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "country should not be empty")
    private String country;

    @Column(nullable = false)
    @NotBlank(message = "city should not be empty")
    private String city;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "locationCode should not be empty")
    private String locationCode;

    @OneToMany(mappedBy = "originLocation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transportation> originTransportations;

    @OneToMany(mappedBy = "destinationLocation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transportation> destinationTransportations;

    public Location(Long id) {
        this.setId(id);
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", locationCode='" + locationCode + '\'' +
                '}';
    }

}
