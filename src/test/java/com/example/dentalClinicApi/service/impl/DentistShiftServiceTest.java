package com.example.dentalClinicApi.service.impl;

import com.example.dentalClinicApi.DTO.DentistShiftDTO;
import com.example.dentalClinicApi.entity.Address;
import com.example.dentalClinicApi.entity.Dentist;
import com.example.dentalClinicApi.entity.Patient;
import com.example.dentalClinicApi.repository.IDentistRepository;
import com.example.dentalClinicApi.repository.IPatientRepository;
import com.example.dentalClinicApi.service.IDentistShiftService;
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
    IPatientRepository iPatientRepository;
    @Autowired
    IDentistRepository iDentistRepository;

    DentistShiftDTO dentShift1 = new DentistShiftDTO();
    DentistShiftDTO dentShiftTest;
    Patient pat;
    Dentist dent;
    Collection<DentistShiftDTO> readAll;

    @BeforeEach
    void setUp() {
        Address address = new Address("Maipú", "Wilde", 1, "Buenos Aires");

        Patient patient = new Patient("Gómez", "Juan", Math.random() + "juan@gamil.com", "30123456", LocalDate.of(2022, 1, 1), address);
        Dentist dentist = new Dentist("123456" + Math.random(), "María", "García");

        pat = iPatientRepository.save(patient);
        dent = iDentistRepository.save(dentist);

        dentShift1.setDate(LocalDate.of(2022, 5, 22));
        dentShift1.setPatient(pat);
        dentShift1.setDentist(dent);
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
        getCreate.setPatient(pat);
        getCreate.setDentist(dent);
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
}