package com.example.thy.service;

import com.example.thy.dto.TransportationDto;
import com.example.thy.entity.Transportation;
import com.example.thy.repository.TransportationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransportationService {

    private final TransportationRepository transportationRepository;
    private final ModelMapper modelMapper;
    public List<TransportationDto> findAll() {
        return transportationRepository.findAll().stream().map(transportation -> modelMapper.map(transportation, TransportationDto.class)).collect(Collectors.toList());
    }

    public TransportationDto findById(Long id) {
        return modelMapper.map(transportationRepository.findById(id).orElse(null), TransportationDto.class);
    }
    public TransportationDto save(TransportationDto transportationDto) {
        Transportation transportation = modelMapper.map(transportationDto, Transportation.class);
        return modelMapper.map(transportationRepository.save(transportation), TransportationDto.class);
    }
    public void deleteById(Long id) {
        transportationRepository.deleteById(id);
    }
}
