package com.example.thy.repository;

import com.example.thy.entity.Location;
import com.example.thy.entity.Transportation;
import com.example.thy.enums.TransportationTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransportationRepository extends JpaRepository<Transportation, Long> {

    List<Transportation> findByOriginLocationId(Long originLocationId);


    @Modifying
    @Transactional
    @Query("UPDATE Transportation e SET e.operationDays = :operationDays, e.transportationType = :transportationType, e.destinationLocation = :dest,e.originLocation =:origin WHERE e.id = :id")
    void updateOperationDays(@Param("id") Long id, @Param("transportationType") TransportationTypeEnum transportationType, @Param("dest") Location dest, @Param("origin") Location origin,
                             @Param("operationDays") Integer[] operationDays);

}
