package com.ramadan.api.controller;

import com.ramadan.api.dto.device.relationdeviceutilisateur.RelationDeviceUtilisateurRequestDto;
import com.ramadan.api.dto.device.relationdeviceutilisateur.RelationDeviceUtilisateurResponseDto;
import com.ramadan.api.dto.device.relationdeviceutilisateur.RelationDeviceUtilisateurUpdateDto;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.services.RelationDeviceUtilisateur.IRelationDeviceUtilisateurService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("${URL-BASE}/relation-device-utilisateurs")
@Tag(name = "Relation Device Utilisateurs", description = "Operations related to relation device utilisateurs")
public class RelationDeviceUtilisateurController {

    @Autowired
    private IRelationDeviceUtilisateurService relationDeviceUtilisateurService;

    @GetMapping("/{uuid}")
    @ApiOperation(value = "Get Relation Device Utilisateur by UUID", notes = "Get relation device utilisateur details by providing its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved relation device utilisateur"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<RelationDeviceUtilisateurResponseDto> getRelationDeviceUtilisateurByUuid(@PathVariable String uuid) {
        try {
            RelationDeviceUtilisateurResponseDto relationDeviceUtilisateur = relationDeviceUtilisateurService.findByUuid(uuid);
            return ResponseEntity.ok(relationDeviceUtilisateur);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get All Relation Device Utilisateurs", notes = "Get all relation device utilisateurs with pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved relation device utilisateurs"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Map<String, Object>> getAllRelationDeviceUtilisateurs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uuid,asc") String[] sort) {
        try {
            Map<String, Object> response = relationDeviceUtilisateurService.getAll(page, size, sort);
            return ResponseEntity.ok(response);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{uuid}")
    @ApiOperation(value = "Delete Relation Device Utilisateur", notes = "Delete an existing relation device utilisateur.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Relation Device Utilisateur deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Void> deleteRelationDeviceUtilisateur(@PathVariable String uuid) {
        try {
            relationDeviceUtilisateurService.deleteRelationDeviceUtilisateur(uuid);
            return ResponseEntity.noContent().build();
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user-devices-list/{userUuid}")
    @ApiOperation(value = "Get Relation Device Utilisateurs by User UUID", notes = "Get all relation device utilisateurs for a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved relation device utilisateurs"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Map<String, Object>> getRelationDeviceUtilisateursByUser(
            @PathVariable String userUuid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uuid,asc") String[] sort) {
        try {
            Map<String, Object> response = relationDeviceUtilisateurService.getAllByUser(userUuid, page, size, sort);
            return ResponseEntity.ok(response);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/device-users-list/{deviceUuid}")
    @ApiOperation(value = "Get Relation Device Utilisateurs by Device UUID", notes = "Get all relation device utilisateurs for a device.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved relation device utilisateurs"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Map<String, Object>> getRelationDeviceUtilisateursByDevice(
            @PathVariable String deviceUuid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uuid,asc") String[] sort) {
        try {
            Map<String, Object> response = relationDeviceUtilisateurService.getByDevice(deviceUuid, page, size, sort);
            return ResponseEntity.ok(response);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping()
    @ApiOperation(value = "Update Relation Device Utilisateur", notes = "Update an existing relation device utilisateur.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Relation Device Utilisateur updated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<RelationDeviceUtilisateurResponseDto> updateRelationDeviceUtilisateur( @Valid @RequestBody RelationDeviceUtilisateurUpdateDto relationDeviceUtilisateurUpdateDto) {
        try {
            RelationDeviceUtilisateurResponseDto updatedRelation = relationDeviceUtilisateurService.updateRelationDeviceUtilisateur(relationDeviceUtilisateurUpdateDto);
            return ResponseEntity.ok(updatedRelation);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @ApiOperation(value = "Create Relation Device Utilisateur", notes = "Create a new relation device utilisateur.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Relation Device Utilisateur created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<RelationDeviceUtilisateurResponseDto> createRelationDeviceUtilisateur(@Valid @RequestBody RelationDeviceUtilisateurRequestDto relationDeviceUtilisateurRequestDto) {
        try {
            RelationDeviceUtilisateurResponseDto createdRelation = relationDeviceUtilisateurService.addRelation(relationDeviceUtilisateurRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRelation);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
