package com.example.dentalClinicApi.controller;

import com.example.dentalClinicApi.DTO.PatientDTO;
import com.example.dentalClinicApi.service.IPatientService;
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

@Tag(name = "Patients", description = "Operations related to patients")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    IPatientService iPatientService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new patient",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "JWT token required", required = true),
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/register")
    public ResponseEntity<?> addPatient(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Object with patient's data to be created")
            @Valid @RequestBody PatientDTO patientDTO) {
        PatientDTO patientCreated = iPatientService.create(patientDTO);
        return new ResponseEntity<>(patientCreated, HttpStatus.OK);
    }

    @Operation(summary = "Get a patient specified by id")
    @GetMapping("/id/{id}")
    public ResponseEntity<?> findPatient(@Parameter(description = "Patient ID to be obtained") @PathVariable Integer id) {
        PatientDTO patientDTO = iPatientService.findOne(id);
        return ResponseEntity.ok(patientDTO);
    }

    @Operation(summary = "Get list of patients")
    @GetMapping()
    public ResponseEntity<Collection<PatientDTO>> findAllPatients() {
        return ResponseEntity.ok(iPatientService.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an existing patient",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "JWT token required", required = true),
            security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePatient(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Object with patient's data to be created")
            @Valid @RequestBody PatientDTO patientDTO,
            @Parameter(description = "Dentist ID to be updated") @PathVariable Integer id) {
        ResponseEntity<?> res;
        if (iPatientService.findOne(patientDTO.getId()) != null) {
            PatientDTO patientUpdated = iPatientService.update(patientDTO, id);
            res = new ResponseEntity<>(patientUpdated, HttpStatus.OK);
        } else {
            res = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return res;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete an existing patient",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "JWT token required", required = true),
            security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@Parameter(description = "Dentist ID to be deleted") @PathVariable Integer id) {
        ResponseEntity<String> res;
        if (iPatientService.findOne(id) != null) {
            iPatientService.delete(id);
            res = new ResponseEntity<>("Paciente eliminado con id: " + id, HttpStatus.OK);
        } else {
            res = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return res;
    }

    @Operation(summary = "Get a patient specified by email")
    @GetMapping("/email/{email}")
    public ResponseEntity<?> findPatientByEmail(@Parameter(description = "Patient email to be obtained") @PathVariable String email) {
        PatientDTO patientDTO = iPatientService.findOnePatientByEmail(email);
        return ResponseEntity.ok(patientDTO);
    }
}
