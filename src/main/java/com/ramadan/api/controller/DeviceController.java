package com.ramadan.api.controller;

import com.ramadan.api.dto.device.DeviceRequestDto;
import com.ramadan.api.dto.device.DeviceResponseDto;
import com.ramadan.api.dto.device.DeviceUpdateDto;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.services.device.IDeviceService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.Map;

@RestController
@RequestMapping("${URL-BASE}/devices")
@Tag(name  = "Devices", description = "Operations related to devices")
public class DeviceController {

    @Autowired
    private IDeviceService deviceService;

    @GetMapping("/{uuid}")
    @ApiOperation(value = "Get Device by UUID", notes = "Get device details by providing its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved device"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<DeviceResponseDto> getDeviceByUuid(@PathVariable String uuid) {
        try {
            DeviceResponseDto device = deviceService.findByUuid(uuid);
            return ResponseEntity.ok(device);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/devices-company-list/{companyUuid}")
    @ApiOperation(value = "Get All Devices by Company UUID", notes = "Get all devices for a company.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved devices"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Map<String, Object>> getAllDevicesByCompany(
            @PathVariable String companyUuid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uuid,asc") String[] sort) {
        try {
            Map<String, Object> response = deviceService.findAllByCompany(companyUuid, page, size, sort);
            return ResponseEntity.ok(response);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @ApiOperation(value = "Create Device", notes = "Create a new device.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Device created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<DeviceResponseDto> createDevice(@Valid @RequestBody DeviceRequestDto deviceRequestDto) {
        try {
            DeviceResponseDto createdDevice = deviceService.saveDevice(deviceRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDevice);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping()
    @ApiOperation(value = "Update Device", notes = "Update an existing device.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device updated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<DeviceResponseDto> updateDevice(@Valid @RequestBody DeviceUpdateDto deviceUpdateDto) {
        try {
            DeviceResponseDto updatedDevice = deviceService.updateDevice(deviceUpdateDto);
            return ResponseEntity.ok(updatedDevice);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{uuid}")
    @ApiOperation(value = "Delete Device", notes = "Delete an existing device.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Device deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Void> deleteDevice(@PathVariable String uuid) {
        try {
            deviceService.deleteDevice(uuid);
            return ResponseEntity.noContent().build();
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get All Devices", notes = "Get all devices with pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved devices"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Map<String, Object>> getAllDevices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uuid,asc") String[] sort) {
        try {
            Map<String, Object> response = deviceService.getAll(page, size, sort);
            return ResponseEntity.ok(response);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
