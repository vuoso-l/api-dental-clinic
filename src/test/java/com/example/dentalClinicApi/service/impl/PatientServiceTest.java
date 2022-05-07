package com.example.dentalClinicApi.service.impl;

import com.example.dentalClinicApi.DTO.PatientDTO;
import com.example.dentalClinicApi.entity.Address;
import com.example.dentalClinicApi.service.IPatientService;
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
class PatientServiceTest {

    @Autowired
    IPatientService iPatientService;

    PatientDTO pat1 = new PatientDTO();
    PatientDTO patTest;
    Address address = new Address("Maipú", "Wilde", 1, "Buenos Aires");
    Collection<PatientDTO> readAll;

    @BeforeEach
    void setUp() {
        pat1.setLastName("Test");
        pat1.setFirstName("Prueba");
        pat1.setEmail(Math.random() + "test@gmail.com");
        pat1.setDni("30456123");
        pat1.setAdmissionDate(LocalDate.of(2022, 1, 1));
        pat1.setAddress(address);
        patTest = iPatientService.create(pat1);
    }

    @Test
    void create() {
        assertNotNull(iPatientService.findOne(patTest.getId()));
        assertEquals(pat1.getFirstName(), patTest.getFirstName());
        assertEquals(pat1.getLastName(), patTest.getLastName());
    }

    @Test
    void findOne() {
        assertNotNull(iPatientService.findOne(patTest.getId()));
    }

    @Test
    void findOnePatientByEmail() {
        assertNotNull(iPatientService.findOnePatientByEmail(patTest.getEmail()));
    }

    @Test
    void update() {
        PatientDTO getCreate = iPatientService.findOne(patTest.getId());
        getCreate.setLastName("Test-1");
        getCreate.setFirstName("Update");
        getCreate.setEmail(Math.random() + "update@gmail.com");
        getCreate.setDni("30456123");
        getCreate.setAdmissionDate(LocalDate.of(2022, 1, 1));
        getCreate.setAddress(address);
        PatientDTO readUpdate = iPatientService.update(getCreate, getCreate.getId());
        assertEquals(iPatientService.findOne(readUpdate.getId()).getFirstName(), "Update");
        assertNotEquals(readUpdate, getCreate);
    }

    @Test
    void delete() {
        assertNotNull(iPatientService.findOne(patTest.getId()));
        iPatientService.delete(patTest.getId());
        assertThrows(Exception.class, () -> iPatientService.findOne(patTest.getId()));
    }

    @Test
    void findAll() {
        readAll = iPatientService.findAll();
        Set<PatientDTO> patientDTOS = new HashSet<>(readAll);
        assertFalse(patientDTOS.isEmpty());
    }
}