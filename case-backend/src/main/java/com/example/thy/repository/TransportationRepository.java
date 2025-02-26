package com.example.thy.repository;

import com.example.thy.entity.Transportation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransportationRepository extends JpaRepository<Transportation, Long> {

    List<Transportation> findByOriginLocationId(Long originLocationId);
}
