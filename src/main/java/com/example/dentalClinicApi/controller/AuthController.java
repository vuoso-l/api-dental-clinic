package com.example.dentalClinicApi.controller;

import com.example.dentalClinicApi.DTO.auth.LoginDTO;
import com.example.dentalClinicApi.DTO.auth.RegisterDTO;
import com.example.dentalClinicApi.entity.auth.Role;
import com.example.dentalClinicApi.entity.auth.User;
import com.example.dentalClinicApi.repository.auth.IRoleRepository;
import com.example.dentalClinicApi.repository.auth.IUserRepository;
import com.example.dentalClinicApi.security.JwtAuthResponseDTO;
import com.example.dentalClinicApi.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Tag(name = "Users", description = "Operations related to users")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    IRoleRepository iRoleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "Logging the user into the api")
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserNameOrEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //se obtiene el token del jwtTokenProvider
        String token = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthResponseDTO(token));
    }

    @Operation(summary = "Create a new user")
    @PostMapping("/signup")
    public ResponseEntity<?> signinUser(@RequestBody RegisterDTO registerDTO) {
        if (iUserRepository.existsByUserName(registerDTO.getUserName())){
            return new ResponseEntity<>("El usuario ya existe", HttpStatus.BAD_REQUEST);
        }

        if (iUserRepository.existsByEmail(registerDTO.getEmail())){
            return new ResponseEntity<>("El usuario ya existe", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(registerDTO.getName());
        user.setUserName(registerDTO.getUserName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Role roles = iRoleRepository.findByName("ROLE_ADMIN").get();
        user.setRoleUser(Collections.singleton(roles));

        iUserRepository.save(user);

        return new ResponseEntity<>("Usuario registrado exitosamente!", HttpStatus.OK);
    }
}
