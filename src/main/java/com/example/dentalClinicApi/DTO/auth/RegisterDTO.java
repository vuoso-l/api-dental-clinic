package com.example.dentalClinicApi.DTO.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    private String name;
    private String userName;
    private String email;
    private String password;

    public RegisterDTO() {
    }

    public RegisterDTO(String name, String userName, String email, String password) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
