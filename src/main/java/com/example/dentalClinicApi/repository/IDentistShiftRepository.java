package com.example.dentalClinicApi.repository;

import com.example.dentalClinicApi.entity.DentistShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDentistShiftRepository extends JpaRepository<DentistShift, Integer> {
}
