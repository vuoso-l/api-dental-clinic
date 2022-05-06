package com.example.dentalClinicApi.repository;

import com.example.dentalClinicApi.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Integer> {

    @Query("select p from Patient p where p.email like %:email%")
    Optional<Patient> findOnePatientByEmail(@Param("email") String email);
}
