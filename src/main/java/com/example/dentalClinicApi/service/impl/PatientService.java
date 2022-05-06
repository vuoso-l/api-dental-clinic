package com.example.dentalClinicApi.service.impl;

import com.example.dentalClinicApi.DTO.PatientDTO;
import com.example.dentalClinicApi.entity.Patient;
import com.example.dentalClinicApi.exception.ResourceNotFoundException;
import com.example.dentalClinicApi.repository.IPatientRepository;
import com.example.dentalClinicApi.service.IPatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PatientService implements IPatientService {

    @Autowired
    IPatientRepository iPatientRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    public PatientDTO create(PatientDTO patientDTO) {
        if(patientDTO == null){
            throw new ResourceNotFoundException("Paciente", "id", patientDTO.getId());
        }

        Patient patient = mapEntity(patientDTO);
        return mapDTO(iPatientRepository.save(patient));
    }

    @Override
    public PatientDTO findOne(Integer id) {
        Patient patient = iPatientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente", "Id", id));
        return mapDTO(patient);
    }

    @Override
    public PatientDTO update(PatientDTO patientDTO, Integer id) {
        Patient patient = iPatientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente", "Id", id));

        patient.setLastName(patientDTO.getLastName());
        patient.setFirstName(patientDTO.getFirstName());
        patient.setEmail(patientDTO.getEmail());
        patient.setDni(patientDTO.getDni());
        patient.setAdmissionDate(patientDTO.getAdmissionDate());
        patient.setAddress(patientDTO.getAddress());

        return mapDTO(iPatientRepository.save(patient));
    }

    @Override
    public void delete(Integer id) {
        Patient patient = iPatientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente", "Id", id));

        iPatientRepository.delete(patient);
    }

    @Override
    public Collection<PatientDTO> findAll() {
        List<Patient> patientList = iPatientRepository.findAll();
        Set<PatientDTO> patientDTOS = new HashSet<>();
        for (Patient patient : patientList) {
            patientDTOS.add(mapper.convertValue(patient, PatientDTO.class));
        }
        return patientDTOS;
    }

    @Override
    public PatientDTO findOnePatientByEmail(String email) {
        PatientDTO patientDTO = null;
        Optional<Patient> patientOptional = iPatientRepository.findOnePatientByEmail(email);
        if (patientOptional.isPresent()) {
            patientDTO = mapper.convertValue(patientOptional, PatientDTO.class);
        }
        return patientDTO;
    }

    private PatientDTO mapDTO(Patient patient) {
        return mapper.convertValue(patient, PatientDTO.class);
    }

    public Patient mapEntity(PatientDTO patientDTO) {
        return mapper.convertValue(patientDTO, Patient.class);
    }
}
