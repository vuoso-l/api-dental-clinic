package com.example.dentalClinicApi.service.impl;

import com.example.dentalClinicApi.DTO.DentistDTO;
import com.example.dentalClinicApi.DTO.DentistShiftDTO;
import com.example.dentalClinicApi.DTO.PatientDTO;
import com.example.dentalClinicApi.entity.Address;
import com.example.dentalClinicApi.entity.Dentist;
import com.example.dentalClinicApi.entity.Patient;
import com.example.dentalClinicApi.repository.IDentistRepository;
import com.example.dentalClinicApi.repository.IPatientRepository;
import com.example.dentalClinicApi.service.IDentistService;
import com.example.dentalClinicApi.service.IDentistShiftService;
import com.example.dentalClinicApi.service.IPatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DentistShiftServiceTest {

    @Autowired
    IDentistShiftService iDentistShiftService;
    @Autowired
    IPatientService iPatientService;
    @Autowired
    IDentistService iDentistService;
    @Autowired
    ObjectMapper mapper;

    DentistShiftDTO dentShift1 = new DentistShiftDTO();
    DentistShiftDTO dentShiftTest;
    PatientDTO pat;
    DentistDTO dent;
    Collection<DentistShiftDTO> readAll;

    @BeforeEach
    void setUp() {
        Address address = new Address("Maipú", "Wilde", 1, "Buenos Aires");

        Patient patient = new Patient("Gómez", "Juan", Math.random() + "juan@gamil.com", "30123456", LocalDate.of(2022, 1, 1), address);
        Dentist dentist = new Dentist("123456" + Math.random(), "María", "García");

        pat = iPatientService.create(mapPatientDTO(patient));
        dent = iDentistService.create(mapDentistDTO(dentist));

        dentShift1.setDate(LocalDate.of(2022, 5, 22));
        dentShift1.setPatient(mapPatientEntity(pat));
        dentShift1.setDentist(mapDentistEntity(dent));
        dentShiftTest = iDentistShiftService.create(dentShift1);
    }

    @Test
    void create() {
            assertNotNull(iDentistShiftService.findOne(dentShiftTest.getId()));
            assertEquals(dentShift1.getPatient(), dentShiftTest.getPatient());
            assertEquals(dentShift1.getDentist(), dentShiftTest.getDentist());
    }

    @Test
    void findOne() {
        assertNotNull(iDentistShiftService.findOne(dentShiftTest.getId()));
    }

    @Test
    void findAll() {
        readAll = iDentistShiftService.findAll();
        Set<DentistShiftDTO> dentistShiftDTOS = new HashSet<>(readAll);
        assertFalse(dentistShiftDTOS.isEmpty());
    }

    @Test
    void update() {
        DentistShiftDTO getCreate = iDentistShiftService.findOne(dentShiftTest.getId());
        getCreate.setDate(LocalDate.of(2023, 1, 1));
        getCreate.setPatient(mapPatientEntity(pat));
        getCreate.setDentist(mapDentistEntity(dent));
        DentistShiftDTO readUpdate = iDentistShiftService.update(getCreate, getCreate.getId());
        assertEquals(iDentistShiftService.findOne(readUpdate.getId()).getDate(), LocalDate.of(2023, 1, 1));
        assertNotEquals(readUpdate, getCreate);
    }

    @Test
    void delete() {
        assertNotNull(iDentistShiftService.findOne(dentShiftTest.getId()));
        iDentistShiftService.delete(dentShiftTest.getId());
        assertThrows(Exception.class, () -> iDentistShiftService.findOne(dentShiftTest.getId()));
    }

    private DentistDTO mapDentistDTO(Dentist dentist) {
        return mapper.convertValue(dentist, DentistDTO.class);
    }
    public Dentist mapDentistEntity(DentistDTO dentistDTO) {
        return mapper.convertValue(dentistDTO, Dentist.class);
    }
    private PatientDTO mapPatientDTO(Patient patient) {
        return mapper.convertValue(patient, PatientDTO.class);
    }
    public Patient mapPatientEntity(PatientDTO patientDTO) {
        return mapper.convertValue(patientDTO, Patient.class);
    }
}