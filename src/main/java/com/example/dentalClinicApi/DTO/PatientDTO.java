package com.example.dentalClinicApi.DTO;

import com.example.dentalClinicApi.entity.Address;
import com.example.dentalClinicApi.entity.DentistShift;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class PatientDTO {
    private Integer id;

    @NotEmpty(message = "No puede estar vacío")
    @Size(min=2, message = "El apellido no puede tener menos de dos caracteres")
    private String lastName;

    @NotEmpty(message = "No puede estar vacío")
    @Size(min=2, message = "El nombre no puede tener menos de dos caracteres")
    private String firstName;

    @NotEmpty(message = "No puede estar vacío")
    @Email(message = "Debe contener formato email. Por ejemplo: ejemplo@ejemplo.com")
    private String email;

    @NotEmpty(message = "No puede estar vacío")
    private String dni;

    private LocalDate admissionDate;

    private Address address;
    private Set<DentistShift> dentistShifts;
}
