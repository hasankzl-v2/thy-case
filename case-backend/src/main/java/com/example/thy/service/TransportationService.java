package com.example.thy.service;

import com.example.thy.entity.Transportation;
import com.example.thy.repository.TransportationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransportationService {

    private final TransportationRepository transportationRepository;

    public List<Transportation> findAll() {
        return transportationRepository.findAll();
    }

    public Transportation findById(Long id) {
        return transportationRepository.findById(id).get();
    }
    public Transportation save(Transportation transportation) {
        return transportationRepository.save(transportation);
    }
    public void deleteById(Long id) {
        transportationRepository.deleteById(id);
    }
}
