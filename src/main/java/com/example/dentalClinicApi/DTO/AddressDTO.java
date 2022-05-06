package com.example.dentalClinicApi.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AddressDTO {
    private Integer id;

    @NotEmpty(message = "No puede estar vaacío")
    @Size(min=2, message = "La dirección no puede tener menos de dos caracteres")
    private String street;

    @NotEmpty(message = "No puede estar vaacío")
    @Size(min=2, message = "La localidad no puede tener menos de dos caracteres")
    private String locality;

    @NotEmpty(message = "No puede estar vaacío")
    private int number;

    @NotEmpty(message = "No puede estar vaacío")
    @Size(min=2, message = "La provincia no puede tener menos de dos caracteres")
    private String province;
}
