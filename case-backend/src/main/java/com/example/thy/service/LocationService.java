package com.example.thy.service;

import com.example.thy.dto.LocationDto;
import com.example.thy.entity.Location;
import com.example.thy.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;

    public List<Location> findAll(){
        return locationRepository.findAll();
    }

    public Location findById(Long id){
        Optional<Location> location = locationRepository.findById(id);
        return location.orElse(null);
    }
    public LocationDto save(LocationDto locationDto){
        Location location = modelMapper.map(locationDto, Location.class);
        return modelMapper.map(locationRepository.save(location),LocationDto.class);
    }

    public void deleteById(Long id){
        locationRepository.deleteById(id);
    }
}
