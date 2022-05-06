package com.example.dentalClinicApi.service;

import com.example.dentalClinicApi.DTO.PatientDTO;

public interface IPatientService extends IBasicCrudService<PatientDTO>{

    PatientDTO findOnePatientByEmail(String email);
}
