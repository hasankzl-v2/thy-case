package com.example.thy.service;

import com.example.thy.dto.LocationDto;
import com.example.thy.dto.request.SaveTransportationRequestDto;
import com.example.thy.dto.TransportationDto;
import com.example.thy.dto.request.SearchTransportationDto;
import com.example.thy.dto.request.UpdateTransportationRequestDto;
import com.example.thy.entity.Location;
import com.example.thy.entity.Transportation;
import com.example.thy.enums.ConstraintEnum;
import com.example.thy.enums.TransportationTypeEnum;
import com.example.thy.exception.*;
import com.example.thy.repository.LocationRepository;
import com.example.thy.repository.TransportationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
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

    @PersistenceContext
    private EntityManager entityManager;


    /*
     * Search Transportation by given data and pagination
     * */
    public Page<TransportationDto> searchTransportations(SearchTransportationDto searchDto, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transportation> query = cb.createQuery(Transportation.class);
        Root<Transportation> root = query.from(Transportation.class);

        List<Predicate> predicates = new ArrayList<>();

        // add predicate if given data exist
        if (searchDto.getId() != null) {
            predicates.add(cb.equal(root.get("id"), searchDto.getId()));
        }
        if (StringUtils.hasText(searchDto.getDestinationLocationCode())) {
            Location byLocationCode = locationRepository.findByLocationCode(searchDto.getDestinationLocationCode());
            predicates.add(cb.equal(root.get("destinationLocation"), byLocationCode));
        }
        if (StringUtils.hasText(searchDto.getOriginLocationCode())) {
            Location byLocationCode = locationRepository.findByLocationCode(searchDto.getOriginLocationCode());
            predicates.add(cb.equal(root.get("originLocation"), byLocationCode));
        }

        if (searchDto.getTransportationType() != null) {
            predicates.add(cb.equal(root.get("transportationType"), searchDto.getTransportationType().toString()));
        }

        if (searchDto.getOperationDays() != null && searchDto.getOperationDays().length > 0) {
            predicates.add(cb.equal(root.get("operationDays"), searchDto.getOperationDays()));

        }

        query.where(cb.and(predicates.toArray(new Predicate[0])));

        // create query for pagination
        TypedQuery<Transportation> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        // get max data size for pagination
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Transportation> countRoot = countQuery.from(Transportation.class);
        countQuery.select(cb.count(countRoot));
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();


        List<Transportation> resultList = typedQuery.getResultList();

        List<TransportationDto> collect = resultList.stream().map(transportation -> modelMapper.map(transportation, TransportationDto.class)).collect(Collectors.toList());

        return new PageImpl<>(collect, pageable, totalCount);
    }

    public Page<TransportationDto> findAll(Pageable pageable) {

        return transportationRepository.findAll(pageable).map(transportation -> modelMapper.map(transportation, TransportationDto.class));
    }

    @Transactional
    public TransportationDto findById(Long id) {
        Optional<Transportation> optional = transportationRepository.findById(id);
        if (optional.isPresent()) {
            log.info("Transportation found with id: {}", id);
            return modelMapper.map(optional.get(), TransportationDto.class);
        } else {
            throw new TransportationNotFoundException("Transportation not found for find", id);
        }
    }

    @Transactional
    public Transportation findByIdv2(Long id) {
        Optional<Transportation> optional = transportationRepository.findById(id);
        if (optional.isPresent()) {
            log.info("Transportation found with id: {}", id);
            return optional.get();
        } else {
            throw new TransportationNotFoundException("Transportation not found for find", id);
        }
    }

    public TransportationDto save(@Valid SaveTransportationRequestDto saveTransportationRequestDto) {

        Transportation transportation = modelMapper.map(saveTransportationRequestDto.convertToTransportationDto(), Transportation.class);
        try {
            transportation = transportationRepository.save(transportation);
        }
        // throws different error messages by instance of error
        catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            if (e.getCause() instanceof ConstraintViolationException) {
                String constraintName = ((ConstraintViolationException) e.getCause()).getConstraintName();
                if (ConstraintEnum.UNIQUE_TRANSPORTATION.getValue().equals(constraintName)) {
                    throw new TransportationAlreadyExistsException(e.getMessage());
                } else if (ConstraintEnum.TRANSPORTATION_OPERATION_DAYS_CHECK.getValue().equals(constraintName)) {
                    throw new TransportationOperationDaysNotValidException(e.getMessage());
                }
            }
            throw e;
        }
        log.info("Saved transportation with id: {}", transportation.getId());
        return modelMapper.map(transportation, TransportationDto.class);
    }

    private Location findLocationForUpdate(Long id) {
        Optional<Location> byId = locationRepository.findById(id);
        if (byId.isEmpty()) {
            throw new LocationNotFoundException("Location not found when updating transportation", id);
        }
        return byId.get();

    }


    @Transactional
    public void deleteById(Long id) {
        if (transportationRepository.existsById(id)) {
            log.info("Deleting transportation with id: {}", id);
            transportationRepository.deleteById(id);
        } else {
            throw new TransportationNotFoundException("Transportation not found for delete", id);
        }
    }

    public List<TransportationDto> findByOriginLocationId(Long startLocationId) {
        List<Transportation> byOriginLocationId = transportationRepository.findByOriginLocationId(startLocationId);
        return byOriginLocationId.stream().map(transportation -> modelMapper.map(transportation, TransportationDto.class)).toList();
    }

    /*
     * update transportation entity with given data
     * */
    @Transactional
    public TransportationDto update(UpdateTransportationRequestDto updateTransportationRequestDto) {
        Optional<Transportation> transportation = transportationRepository.findById(updateTransportationRequestDto.getId());
        // if not present throw exception
        if (transportation.isEmpty()) {
            throw new TransportationNotFoundException("Transportation not found for update", updateTransportationRequestDto.getId());
        }
        Transportation savedTransportation = transportation.get();

        try {
            if (updateTransportationRequestDto.getTransportationType() != null) {
                savedTransportation.setTransportationType(updateTransportationRequestDto.getTransportationType());
            }
            if (updateTransportationRequestDto.getOriginLocationId() != null) {
                Location location = findLocationForUpdate(updateTransportationRequestDto.getOriginLocationId());
                savedTransportation.setOriginLocation(location);
            }
            if (updateTransportationRequestDto.getDestinationLocationId() != null) {
                Location location = findLocationForUpdate(updateTransportationRequestDto.getDestinationLocationId());
                savedTransportation.setDestinationLocation(location);
            }
            if (updateTransportationRequestDto.getOperationDays() != null && updateTransportationRequestDto.getOperationDays().length > 0) {
                savedTransportation.setOperationDays(updateTransportationRequestDto.getOperationDays());
            }

            transportationRepository.save(savedTransportation);
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            throw new TransportationAlreadyExistsException(e.getMessage());
        }
        return modelMapper.map(savedTransportation, TransportationDto.class);
    }

}
