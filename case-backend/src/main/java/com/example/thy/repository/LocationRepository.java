package com.example.thy.repository;

import com.example.thy.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface LocationRepository  extends JpaRepository<Location,Long> {


}
