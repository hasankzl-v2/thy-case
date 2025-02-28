package com.example.thy.service;

import com.example.thy.dto.LocationDto;
import com.example.thy.dto.request.SaveTransportationRequestDto;
import com.example.thy.dto.TransportationDto;
import com.example.thy.dto.request.UpdateTransportationRequestDto;
import com.example.thy.entity.Location;
import com.example.thy.entity.Transportation;
import com.example.thy.exception.*;
import com.example.thy.repository.LocationRepository;
import com.example.thy.repository.TransportationRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class TransportationService {

    private final TransportationRepository transportationRepository;
    private final ModelMapper modelMapper;
    private final LocationRepository locationRepository;

    public List<TransportationDto> findAll() {
        return transportationRepository.findAll().stream().map(transportation -> modelMapper.map(transportation, TransportationDto.class)).collect(Collectors.toList());
    }

    public TransportationDto findById(Long id) {
        Optional<Transportation> optional = transportationRepository.findById(id);
        if (optional.isPresent()) {
            return modelMapper.map(optional.get(), TransportationDto.class);
        } else {
            throw new TransportationNotFoundException("Transportation not found for find", id);
        }
    }

    public TransportationDto save(@Valid SaveTransportationRequestDto saveTransportationRequestDto) {

        Transportation transportation = modelMapper.map(saveTransportationRequestDto.convertToDto(), Transportation.class);
        try {
            transportation = transportationRepository.save(transportation);
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            if (e.getCause() instanceof ConstraintViolationException) {
                String constraintName = ((ConstraintViolationException) e.getCause()).getConstraintName();
                if (constraintName.equals("unique_transportation")) {
                    throw new TransportationAlreadyExistsException(e.getMessage());
                } else if (constraintName.equals("transportation_operation_days_check")) {
                    throw new TransportationOperationDaysNotValidException(e.getMessage());
                }
            }
            throw e;
        }
        return modelMapper.map(transportation, TransportationDto.class);
    }

    private Location findLocationForUpdate(Long id) {
        Optional<Location> byId = locationRepository.findById(id);
        if (byId.isEmpty()) {
            throw new LocationNotFoundException("Location not found when updating transportation", id);
        }
        return byId.get();

    }

    public TransportationDto update(UpdateTransportationRequestDto saveTransportationRequestDto) {
        Optional<Transportation> transportation = transportationRepository.findById(saveTransportationRequestDto.getId());
        // if not present throw exception
        if (transportation.isEmpty()) {
            throw new TransportationNotFoundException("Transportation not found for update", saveTransportationRequestDto.getId());
        }
        Transportation savedTransportation = transportation.get();

        try {
            if (savedTransportation.getTransportationType() != null) {
                savedTransportation.setTransportationType(saveTransportationRequestDto.getTransportationType());
            }
            if (saveTransportationRequestDto.getDestinationLocationId() != null) {
                Location location = findLocationForUpdate(saveTransportationRequestDto.getDestinationLocationId());
                savedTransportation.setDestinationLocation(location);
            }
            if (saveTransportationRequestDto.getOriginLocationId() != null) {
                Location location = findLocationForUpdate(saveTransportationRequestDto.getOriginLocationId());
                savedTransportation.setOriginLocation(location);
            }
            savedTransportation = transportationRepository.save(savedTransportation);
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            throw new TransportationAlreadyExistsException(e.getMessage());
        }
        return modelMapper.map(savedTransportation, TransportationDto.class);
    }

    @Transactional
    public void deleteById(Long id) {
        if (transportationRepository.existsById(id)) {
            transportationRepository.deleteById(id);
        } else {
            throw new TransportationNotFoundException("Transportation not found for delete", id);
        }
    }

    public List<TransportationDto> findByOriginLocationId(Long startLocationId) {
        List<Transportation> byOriginLocationId = transportationRepository.findByOriginLocationId(startLocationId);
        return byOriginLocationId.stream().map(transportation -> modelMapper.map(transportation, TransportationDto.class)).toList();
    }
}
