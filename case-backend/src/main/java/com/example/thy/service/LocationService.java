package com.example.thy.service;

import com.example.thy.dto.LocationDto;
import com.example.thy.dto.request.SaveLocationRequestDto;
import com.example.thy.dto.request.UpdateLocationRequestDto;
import com.example.thy.entity.Location;
import com.example.thy.exception.LocationAlreadyExistsException;
import com.example.thy.exception.LocationNotFoundException;
import com.example.thy.repository.LocationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class LocationService {

    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;


    @PersistenceContext
    private EntityManager entityManager;

    /*
     * search location with given data and pagination
     * */
    public Page<LocationDto> searchLocations(LocationDto searchDto, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Location> query = cb.createQuery(Location.class);
        Root<Location> root = query.from(Location.class);

        List<Predicate> predicates = new ArrayList<>();

        // add predicate if given data exist
        if (searchDto.getId() != null && searchDto.getId() > 0) {
            predicates.add(cb.equal(root.get("id"), searchDto.getId()));
        }
        if (StringUtils.hasText(searchDto.getName())) {
            predicates.add(cb.like(cb.lower(root.get("name")), searchDto.getName().toLowerCase() + "%"));
        }

        if (StringUtils.hasText(searchDto.getCountry())) {
            predicates.add(cb.like(cb.lower(root.get("country")), searchDto.getCountry().toLowerCase() + "%"));
        }

        if (StringUtils.hasText(searchDto.getCity())) {
            predicates.add(cb.like(cb.lower(root.get("city")), searchDto.getCity().toLowerCase() + "%"));
        }

        if (StringUtils.hasText(searchDto.getLocationCode())) {
            predicates.add(cb.like(cb.lower(root.get("locationCode")), searchDto.getLocationCode().toLowerCase() + "%"));
        }
        query.where(cb.and(predicates.toArray(new Predicate[0])));

        // create query for pagination
        TypedQuery<Location> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        // get max data size for pagination
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Location> countRoot = countQuery.from(Location.class);
        countQuery.select(cb.count(countRoot));
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();


        List<Location> resultList = typedQuery.getResultList();

        // map result in dto
        List<LocationDto> collect = resultList.stream().map(location -> modelMapper.map(location, LocationDto.class)).collect(Collectors.toList());

        return new PageImpl<>(collect, pageable, totalCount);

    }


    public Page<LocationDto> findAll(Pageable pageable) {

        return locationRepository.findAll(pageable)
                .map(location -> modelMapper.map(location, LocationDto.class));
    }

    public LocationDto findById(Long id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isPresent()) {
            log.info("Location not found with id: {}", id);
            return modelMapper.map(location.get(), LocationDto.class);
        } else {
            throw new LocationNotFoundException("Location not found", id);
        }
    }

    public LocationDto save(@Valid SaveLocationRequestDto saveLocationRequestDto) {

        Location location = modelMapper.map(saveLocationRequestDto, Location.class);
        try {
            location = locationRepository.save(location);
        }
        // if data integrity exist throw specific error
        catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            throw new LocationAlreadyExistsException(e.getMessage());
        }
        log.info("Location saved with id {}", location.getId());
        return modelMapper.map(location, LocationDto.class);
    }

    @Transactional
    public void deleteById(Long id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
        } else {
            throw new LocationNotFoundException("Location not found", id);
        }
        log.info("Location deleted with id {}", id);
    }

    /*
     * update location by only given data
     * */
    public LocationDto update(UpdateLocationRequestDto updateLocationRequestDto) {
        Optional<Location> location = locationRepository.findById(updateLocationRequestDto.getId());
        if (location.isEmpty()) {
            throw new LocationNotFoundException("Location not found for update ", updateLocationRequestDto.getId());
        }
        Location savedLocation = location.get();

        try {
            if (updateLocationRequestDto.getName() != null) {
                savedLocation.setName(updateLocationRequestDto.getName());
            }
            if (updateLocationRequestDto.getLocationCode() != null) {
                savedLocation.setLocationCode(updateLocationRequestDto.getLocationCode());
            }
            if (updateLocationRequestDto.getCity() != null) {
                savedLocation.setCity(updateLocationRequestDto.getCity());
            }
            if (updateLocationRequestDto.getCountry() != null) {
                savedLocation.setCountry(updateLocationRequestDto.getCountry());
            }
            savedLocation = locationRepository.save(savedLocation);
        }
        // if data integrity exist throw specific error
        catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            throw new LocationAlreadyExistsException(e.getMessage());
        }
        log.info("Location updated with id {}", savedLocation.getId());
        return modelMapper.map(savedLocation, LocationDto.class);
    }

    public LocationDto findByLocationCode(String locationCode) {

        Location byLocationCode = locationRepository.findByLocationCode(locationCode);

        return modelMapper.map(byLocationCode, LocationDto.class);
    }
}
