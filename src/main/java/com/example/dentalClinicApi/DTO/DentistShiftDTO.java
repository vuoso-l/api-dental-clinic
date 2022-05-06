package com.example.dentalClinicApi.DTO;

import com.example.dentalClinicApi.entity.Dentist;
import com.example.dentalClinicApi.entity.Patient;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
public class DentistShiftDTO {
    private Integer id;

    @NotEmpty(message = "No puede estar vaacío")
    private LocalDate date;
    private Patient patient;
    private Dentist dentist;
}
