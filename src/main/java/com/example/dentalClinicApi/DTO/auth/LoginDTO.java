package com.example.dentalClinicApi.DTO.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

    private String userNameOrEmail;
    private String password;


}
