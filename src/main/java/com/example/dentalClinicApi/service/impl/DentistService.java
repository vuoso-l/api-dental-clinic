package com.example.dentalClinicApi.service.impl;

import com.example.dentalClinicApi.DTO.DentistDTO;
import com.example.dentalClinicApi.entity.Dentist;
import com.example.dentalClinicApi.exception.ResourceNotFoundException;
import com.example.dentalClinicApi.repository.IDentistRepository;
import com.example.dentalClinicApi.service.IDentistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DentistService implements IDentistService {

    @Autowired
    IDentistRepository iDentistRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    public DentistDTO create(DentistDTO dentistDTO) {
        if(dentistDTO == null){
            throw new ResourceNotFoundException("Odontólogo", "id", dentistDTO.getId());
        }

        Dentist dentist = mapEntity(dentistDTO);
        return mapDTO(iDentistRepository.save(dentist));
    }

    @Override
    public DentistDTO findOne(Integer id) {
        Dentist dentist = iDentistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Odontólogo", "Id", id));
        return mapDTO(dentist);
    }

    @Override
    public DentistDTO update(DentistDTO dentistDTO, Integer id) {
        Dentist dentist = iDentistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Odontólogo", "Id", id));

        dentist.setRegistrationNumber(dentistDTO.getRegistrationNumber());
        dentist.setFirstName(dentistDTO.getFirstName());
        dentist.setLastName(dentistDTO.getLastName());

        return mapDTO(iDentistRepository.save(dentist));
    }

    @Override
    public void delete(Integer id) {
        Dentist dentist = iDentistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Odontólogo", "Id", id));

        iDentistRepository.delete(dentist);
    }

    @Override
    public Collection<DentistDTO> findAll() {
        List<Dentist> dentistList = iDentistRepository.findAll();
        Set<DentistDTO> dentistsDTO = new HashSet<>();
        for (Dentist dentist : dentistList) {
            dentistsDTO.add(mapper.convertValue(dentist, DentistDTO.class));
        }
        return dentistsDTO;
    }

    private DentistDTO mapDTO(Dentist dentist) {
        return mapper.convertValue(dentist, DentistDTO.class);
    }

    public Dentist mapEntity(DentistDTO dentistDTO) {
        return mapper.convertValue(dentistDTO, Dentist.class);
    }
}
