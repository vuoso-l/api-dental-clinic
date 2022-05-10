package com.example.dentalClinicApi.controller;

import com.example.dentalClinicApi.DTO.DentistShiftDTO;
import com.example.dentalClinicApi.service.IDentistShiftService;
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

@Tag(name = "DentistShifts", description = "Operations related to dentistShifts")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/dentistShift")
public class DentistShiftController {

    @Autowired
    IDentistShiftService iDentistShiftService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new dentistShift",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "JWT token required", required = true),
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/api/register")
    public ResponseEntity<?> addDentistShift(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Object with dentistShift's data to be created")
            @Valid @RequestBody DentistShiftDTO dentistShiftDTO) {
        DentistShiftDTO dentistShiftCreated = iDentistShiftService.create(dentistShiftDTO);
        return new ResponseEntity<>(dentistShiftCreated, HttpStatus.OK);
    }

    @Operation(summary = "Get a dentistShift specified by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> findDentistShift(@Parameter(description = "DentistShift ID to be obtained") @PathVariable Integer id) {
        DentistShiftDTO dentistShiftDTO = iDentistShiftService.findOne(id);
        return new ResponseEntity<>(dentistShiftDTO, HttpStatus.OK);
    }

    @Operation(summary = "Get list of dentistShifts")
    @GetMapping()
    public ResponseEntity<Collection<DentistShiftDTO>> findAllDentistShifts() {
        return ResponseEntity.ok(iDentistShiftService.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an existing dentistShift",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "JWT token required", required = true),
            security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDentistShift(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Object with dentistShift's data to be created")
            @Valid @RequestBody DentistShiftDTO dentistShiftDTO,
            @Parameter(description = "DentistShif ID to be updated") @PathVariable Integer id) {
        ResponseEntity<?> res;
        if (iDentistShiftService.findOne(dentistShiftDTO.getId()) != null) {
            DentistShiftDTO dentistShiftUpdated = iDentistShiftService.update(dentistShiftDTO, id);
            res = new ResponseEntity<>(dentistShiftUpdated, HttpStatus.OK);
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
    public ResponseEntity<?> deleteDentistShift(@Parameter(description = "DentistShift ID to be deleted") @PathVariable Integer id) {
        ResponseEntity<String> res;
        if (iDentistShiftService.findOne(id) != null) {
            iDentistShiftService.delete(id);
            res = new ResponseEntity<>("Turno eliminado con id: " + id, HttpStatus.OK);
        } else {
            res = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return res;
    }
}
