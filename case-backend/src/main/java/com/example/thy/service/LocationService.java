package com.example.thy.service;

import com.example.thy.dto.LocationDto;
import com.example.thy.entity.Location;
import com.example.thy.exception.LocationAlreadyExistsException;
import com.example.thy.exception.LocationNotFoundException;
import com.example.thy.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;

    public List<LocationDto> findAll(){

        return locationRepository.findAll().stream().map(location -> modelMapper.map(location, LocationDto.class)).collect(Collectors.toList());
    }

    public LocationDto findById(Long id){
        Optional<Location> location = locationRepository.findById(id);
        if (location.isPresent()) {
            return modelMapper.map(location.get(), LocationDto.class);
        }
        else {
            throw new LocationNotFoundException("Location not found",id);
        }
    }
    public LocationDto save(LocationDto locationDto){
        Location location = modelMapper.map(locationDto, Location.class);
        try {
            location = locationRepository.save(location);
        }catch (DataIntegrityViolationException e){
            log.error(e.getMessage());
            throw new LocationAlreadyExistsException(e.getMessage());
        }
        return modelMapper.map(location,LocationDto.class);
    }

    @Transactional
    public void deleteById(Long id){
        if(locationRepository.existsById(id)){
            locationRepository.deleteById(id);
        }else{
            throw new LocationNotFoundException("Location not found",id);
        }
    }
}
