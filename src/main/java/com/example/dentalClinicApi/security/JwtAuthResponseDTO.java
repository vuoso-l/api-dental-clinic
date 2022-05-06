package com.example.dentalClinicApi.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponseDTO {

    private String tokenAccess;
    private String typeToken = "Bearer";

    public JwtAuthResponseDTO(String tokenAccess) {
        this.tokenAccess = tokenAccess;
    }
}
