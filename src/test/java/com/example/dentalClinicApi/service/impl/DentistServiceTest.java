package com.example.dentalClinicApi.service;

import com.example.dentalClinicApi.DTO.DentistDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DentistServiceTest {

    @Autowired
    IDentistService iDentistService;

    DentistDTO dent1 = new DentistDTO();
    DentistDTO dentTest;
    Collection<DentistDTO> readAll;

    @BeforeEach
    void setUp() {
        dent1.setRegistrationNumber("24231" + Math.random());
        dent1.setFirstName("reTest!!");
        dent1.setLastName("Test-4");
        dentTest = iDentistService.create(dent1);
    }

    @Test
    void create() {
        assertNotNull(iDentistService.findOne(dentTest.getId()));
        assertEquals(dent1.getFirstName(), dentTest.getFirstName());
        assertEquals(dent1.getLastName(), dentTest.getLastName());
    }

    @Test
    void findById() {
        assertNotNull(iDentistService.findOne(dentTest.getId()));
    }

    @Test
    void update() {
        DentistDTO getCreate = iDentistService.findOne(dentTest.getId());
        getCreate.setRegistrationNumber("998877" + Math.random());
        getCreate.setFirstName("Update");
        getCreate.setLastName("Test-1");
        DentistDTO readUpdate = iDentistService.update(getCreate, getCreate.getId());
        assertEquals(iDentistService.findOne(readUpdate.getId()).getFirstName(), "Update");
        assertNotEquals(readUpdate, getCreate);
    }

    @Test
    void delete() {
        assertNotNull(iDentistService.findOne(dentTest.getId()));
        iDentistService.delete(dentTest.getId());
        assertThrows(Exception.class, () -> iDentistService.findOne(dentTest.getId()));
    }

    @Test
    void findAll() {
        readAll = iDentistService.findAll();
        Set<DentistDTO> dentistsDTO = new HashSet<>(readAll);
        assertFalse(dentistsDTO.isEmpty());
    }
}