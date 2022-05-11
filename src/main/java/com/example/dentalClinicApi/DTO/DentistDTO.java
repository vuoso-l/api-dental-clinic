package com.example.dentalClinicApi.DTO;

import com.example.dentalClinicApi.entity.DentistShift;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class DentistDTO {
    private Integer id;

    @NotEmpty(message = "No puede estar vacío")
    @Size(min=4, max = 10, message = "La matrícula debe tener un mínimo de 4 números y un máximo de 10 números")
    private String registrationNumber;

    @NotEmpty(message = "No puede estar vacío")
    @Size(min=2, message = "El nombre no puede tener menos de dos caracteres")
    private String firstName;

    @NotEmpty(message = "No puede estar vacío")
    @Size(min=2, message = "El apellido no puede tener menos de dos caracteres")
    private String lastName;

    private Set<DentistShift> dentistShifts;
}
