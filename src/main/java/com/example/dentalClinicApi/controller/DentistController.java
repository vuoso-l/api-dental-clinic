package com.example.dentalClinicApi.controller;

import com.example.dentalClinicApi.DTO.DentistDTO;
import com.example.dentalClinicApi.service.IDentistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Tag(name = "Dentists", description = "Operations related to dentists")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/dentist")
public class DentistController {

    @Autowired
    IDentistService iDentistService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new dentist",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "JWT token required", required = true),
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/register")
    public ResponseEntity<?> addDentist(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Object with dentist's data to be created")
            @RequestBody @Valid DentistDTO dentistDTO) {
        DentistDTO dentistCreated = iDentistService.create(dentistDTO);
        return new ResponseEntity<>(dentistCreated, HttpStatus.OK);
    }

    @Operation(summary = "Get a dentist specified by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> findDentist(@Parameter(description = "Dentist ID to be obtained") @PathVariable Integer id) {
        DentistDTO dentistDTO = iDentistService.findOne(id);
        return new ResponseEntity<>(dentistDTO, HttpStatus.OK);
    }

    @Operation(summary = "Get list of dentists")
    @GetMapping()
    public ResponseEntity<Collection<DentistDTO>> findAllDentists() {
        return ResponseEntity.ok(iDentistService.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an existing dentist",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "JWT token required", required = true),
            security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDentist(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Object with dentist's data to be created")
            @Valid @RequestBody DentistDTO dentistDTO,
            @Parameter(description = "Dentist ID to be updated") @PathVariable Integer id) {
        ResponseEntity<?> res;
        if (iDentistService.findOne(dentistDTO.getId()) != null) {
            DentistDTO dentistUpdated = iDentistService.update(dentistDTO, id);
            res = new ResponseEntity<>(dentistUpdated, HttpStatus.OK);
        } else {
            res = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return res;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete an existing dentist",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "JWT token required", required = true),
            security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDentist(@Parameter(description = "Dentist ID to be deleted") @PathVariable Integer id) {
        ResponseEntity<String> res;
        if (iDentistService.findOne(id) != null) {
            iDentistService.delete(id);
            res = new ResponseEntity<>("Odontólogo eliminado con id: " + id, HttpStatus.OK);
        } else {
            res = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return res;
    }
}
