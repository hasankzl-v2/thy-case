package com.example.thy.repository;

import com.example.thy.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface LocationRepository  extends JpaRepository<Location,Long> {

    Location findByLocationCode(String locationCode);

    Page<Location> findAll(Pageable pageable);
}
