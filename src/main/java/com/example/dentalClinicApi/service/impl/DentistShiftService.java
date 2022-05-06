package com.example.dentalClinicApi.service.impl;

import com.example.dentalClinicApi.DTO.DentistShiftDTO;
import com.example.dentalClinicApi.entity.DentistShift;
import com.example.dentalClinicApi.exception.ResourceNotFoundException;
import com.example.dentalClinicApi.repository.IDentistShiftRepository;
import com.example.dentalClinicApi.service.IDentistShiftService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DentistShiftService implements IDentistShiftService {

    @Autowired
    IDentistShiftRepository iDentistShiftRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    public DentistShiftDTO create(DentistShiftDTO dentistShiftDTO) {

        if(dentistShiftDTO.getPatient() == null){
            throw new ResourceNotFoundException("Paciente", "id", dentistShiftDTO.getPatient().getId());
        }

        if(dentistShiftDTO.getDentist() == null){
            throw new ResourceNotFoundException("Odontólogo", "id", dentistShiftDTO.getDentist().getId());
        }

        DentistShift dentistShift = mapEntity(dentistShiftDTO);

        return mapDTO(iDentistShiftRepository.save(dentistShift));
    }

    @Override
    public DentistShiftDTO findOne(Integer id) {
        DentistShift dentistShift = iDentistShiftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno", "Id", id));

        return mapDTO(dentistShift);
    }

    @Override
    public Collection<DentistShiftDTO> findAll() {
        List<DentistShift> dentistShiftList = iDentistShiftRepository.findAll();
        Set<DentistShiftDTO> dentistShiftDTOS = new HashSet<>();
        for (DentistShift dentistShift : dentistShiftList) {
            dentistShiftDTOS.add(mapper.convertValue(dentistShift, DentistShiftDTO.class));
        }
        return dentistShiftDTOS;
    }

    @Override
    public DentistShiftDTO update(DentistShiftDTO dentistShiftDTO, Integer id) {
        DentistShift dentistShift = iDentistShiftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno", "Id", id));

        dentistShift.setDate(dentistShiftDTO.getDate());

        return mapDTO(iDentistShiftRepository.save(dentistShift));
    }

    @Override
    public void delete(Integer id) {
        DentistShift dentistShift = iDentistShiftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno", "Id", id));

        iDentistShiftRepository.delete(dentistShift);
    }

    private DentistShiftDTO mapDTO(DentistShift dentistShift) {
        return mapper.convertValue(dentistShift, DentistShiftDTO.class);
    }

    public DentistShift mapEntity(DentistShiftDTO dentistShiftDTO) {
        return mapper.convertValue(dentistShiftDTO, DentistShift.class);
    }
}
