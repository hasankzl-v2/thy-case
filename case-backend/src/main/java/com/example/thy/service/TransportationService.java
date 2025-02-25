package com.example.thy.service;

import com.example.thy.dto.LocationDto;
import com.example.thy.dto.TransportationDto;
import com.example.thy.entity.Location;
import com.example.thy.entity.Transportation;
import com.example.thy.exception.*;
import com.example.thy.repository.LocationRepository;
import com.example.thy.repository.TransportationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
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

    public TransportationDto save(TransportationDto transportationDto) {
        validateTransportationDataBeforeSave(transportationDto);
        if (transportationDto.getId() != null) {
            throw new GeneralException("should not send id when saving transportation");
        }
        Transportation transportation = modelMapper.map(transportationDto, Transportation.class);
        try {
            transportation = transportationRepository.save(transportation);
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            throw new TransportationAlreadyExistsException(e.getMessage());
        }
        return modelMapper.map(transportation, TransportationDto.class);
    }

    private Location findLocationForUpdate(LocationDto sourceLocation) {
        Optional<Location> byId = locationRepository.findById(sourceLocation.getId());
        if (byId.isEmpty()) {
            throw new LocationNotFoundException("Location not found when updating transportation", sourceLocation.getId());
        }
        return byId.get();

    }

    public TransportationDto update(TransportationDto transportationDto) {
        validateTransportationDataBeforeUpdate(transportationDto);
        Optional<Transportation> transportation = transportationRepository.findById(transportationDto.getId());
        // if not present throw exception
        if (transportation.isEmpty()) {
            throw new TransportationNotFoundException("Transportation not found for update", transportationDto.getId());
        }
        Transportation savedTransportation = transportation.get();

        try {
            if (savedTransportation.getTransportationType() != null) {
                savedTransportation.setTransportationType(transportationDto.getTransportationType());
            }
            if (transportationDto.getDestinationLocation() != null && transportationDto.getDestinationLocation().getId() != null) {
                Location location = findLocationForUpdate(transportationDto.getDestinationLocation());
                savedTransportation.setDestinationLocation(location);
            }
            if (transportationDto.getOriginLocation() != null && transportationDto.getOriginLocation().getId() != null) {
                Location location = findLocationForUpdate(transportationDto.getOriginLocation());
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

    private void validateTransportationDataBeforeSave(TransportationDto transportationDto) {
        if(transportationDto.getOriginLocation() != null && transportationDto.getOriginLocation().getId() != null
        && transportationDto.getDestinationLocation() != null && transportationDto.getDestinationLocation().getId() != null
        ) {
            if(transportationDto.getOriginLocation().getId().equals(transportationDto.getDestinationLocation().getId())) {
                throw new GeneralException("transport location and destination location should not be the same");
            }
        }
    }

    private void validateTransportationDataBeforeUpdate(TransportationDto transportationDto) {
        if(transportationDto.getOriginLocation() != null && transportationDto.getOriginLocation().getId() != null
                && transportationDto.getDestinationLocation() != null && transportationDto.getDestinationLocation().getId() != null
        ) {
            if(transportationDto.getOriginLocation().getId().equals(transportationDto.getDestinationLocation().getId())) {
                throw new GeneralException("transport location and destination location should not be the same");
            }
        }
    }
}
