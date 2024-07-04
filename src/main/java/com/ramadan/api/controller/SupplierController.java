package com.ramadan.api.controller;

import com.ramadan.api.dto.supplier.SupplierRequestDto;
import com.ramadan.api.dto.supplier.SupplierResponseDto;
import com.ramadan.api.dto.supplier.SupplierUpdateDto;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.services.supplier.ISupplierService;
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
@RequestMapping("${URL-BASE}/suppliers")
@Tag(name = "Suppliers", description = "Operations related to suppliers")
public class SupplierController {

    @Autowired
    private ISupplierService supplierService;

    @GetMapping("/{uuid}")
    @ApiOperation(value = "Get Supplier by UUID", notes = "Get supplier details by providing its UUID.")
    @ApiResponses(value = {
           @ApiResponse( responseCode = "200",  description = "Successfully retrieved supplier"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<SupplierResponseDto> getSupplierByUuid(@PathVariable String uuid) {
        try {
            SupplierResponseDto supplier = supplierService.findByUuid(uuid);
            return ResponseEntity.ok(supplier);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/suppliers-company-list/{companyUuid}")
    @ApiOperation(value = "Get All Suppliers by Company UUID", notes = "Get all suppliers for a company.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved suppliers"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Map<String, Object>> getAllSuppliersByCompany(
            @PathVariable String companyUuid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uuid,asc") String[] sort) {
        try {
            Map<String, Object> response = supplierService.findAllByCompany(companyUuid, page, size, sort);
            return ResponseEntity.ok(response);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @ApiOperation(value = "Create Supplier", notes = "Create a new supplier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Supplier created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<SupplierResponseDto> createSupplier(@Valid @RequestBody SupplierRequestDto supplierRequestDto) {
        try {
            SupplierResponseDto createdSupplier = supplierService.saveSupplier(supplierRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSupplier);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping()
    @ApiOperation(value = "Update Supplier", notes = "Update an existing supplier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier updated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<SupplierResponseDto> updateSupplier( @Valid @RequestBody SupplierUpdateDto supplierRequestDto) {
        try {
            SupplierResponseDto updatedSupplier = supplierService.updateSupplier(supplierRequestDto);
            return ResponseEntity.ok(updatedSupplier);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{uuid}")
    @ApiOperation(value = "Delete Supplier", notes = "Delete an existing supplier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Supplier deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Void> deleteSupplier(@PathVariable String uuid) {
        try {
            supplierService.deleteSupplier(uuid);
            return ResponseEntity.noContent().build();
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
