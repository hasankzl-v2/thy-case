package com.example.thy.service;

import com.example.thy.dto.TransportationDto;
import com.example.thy.entity.Transportation;
import com.example.thy.exception.LocationAlreadyExistsException;
import com.example.thy.exception.LocationNotFoundException;
import com.example.thy.exception.TransportationAlreadyExistsException;
import com.example.thy.exception.TransportationNotFoundException;
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
    public List<TransportationDto> findAll() {
        return transportationRepository.findAll().stream().map(transportation -> modelMapper.map(transportation, TransportationDto.class)).collect(Collectors.toList());
    }

    public TransportationDto findById(Long id) {
        Optional<Transportation> optional = transportationRepository.findById(id);
        if (optional.isPresent()) {
            return modelMapper.map(optional.get(), TransportationDto.class);
        }else {
            throw new TransportationNotFoundException("Transportation not found for find",id);
        }
    }
    public TransportationDto save(TransportationDto transportationDto) {
        Transportation transportation = modelMapper.map(transportationDto, Transportation.class);
        try{
            transportation = transportationRepository.save(transportation);
        }catch (DataIntegrityViolationException e){
            log.error(e.getMessage());
            throw new TransportationAlreadyExistsException(e.getMessage());
        }
        return modelMapper.map(transportation, TransportationDto.class);
    }
    @Transactional
    public void deleteById(Long id) {
        if(transportationRepository.existsById(id)){
            transportationRepository.deleteById(id);
        }else{
            throw new TransportationNotFoundException("Transportation not found for delete",id);
        }
    }
}
