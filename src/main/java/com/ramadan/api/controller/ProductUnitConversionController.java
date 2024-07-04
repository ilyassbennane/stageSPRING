package com.ramadan.api.controller;

import com.ramadan.api.dto.produit.ProductUnitConversion.ProductUnitConversionRequestDto;
import com.ramadan.api.dto.produit.ProductUnitConversion.ProductUnitConversionResponseDto;
import com.ramadan.api.dto.produit.ProductUnitConversion.ProductUnitConversionUpdateDto;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ErrorCode;
import com.ramadan.api.services.unitProduct.IProductUnitConversionService;
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
@RequestMapping("${URL-BASE}/product-unit-conversions")
@Tag(name = "Product Unit Conversions", description = "Operations related to product unit conversions")
public class ProductUnitConversionController {

    @Autowired
    private IProductUnitConversionService productUnitConversionService;

    @GetMapping("/{uuid}")
    @ApiOperation(value = "Get Product Unit Conversion by UUID", notes = "Get product unit conversion details by providing its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product unit conversion"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<ProductUnitConversionResponseDto> getProductUnitConversionByUuid(@PathVariable String uuid) {
        try {
            ProductUnitConversionResponseDto unitConversion = productUnitConversionService.findByUuid(uuid);
            return ResponseEntity.ok(unitConversion);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get All Product Unit Conversions", notes = "Get all product unit conversions with pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product unit conversions"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Map<String, Object>> getAllProductUnitConversions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uuid,asc") String[] sort) {
        try {
            Map<String, Object> response = productUnitConversionService.findAll(page, size, sort);
            return ResponseEntity.ok(response);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @ApiOperation(value = "Create Product Unit Conversion", notes = "Create a new product unit conversion.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product Unit Conversion created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<ProductUnitConversionResponseDto> createProductUnitConversion(@Valid @RequestBody ProductUnitConversionRequestDto requestDto) {
        try {
            ProductUnitConversionResponseDto createdUnitConversion = productUnitConversionService.saveUnitConversion(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUnitConversion);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping()
    @ApiOperation(value = "Update Product Unit Conversion", notes = "Update an existing product unit conversion.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product Unit Conversion updated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<ProductUnitConversionResponseDto> updateProductUnitConversion( @Valid @RequestBody ProductUnitConversionUpdateDto requestDto) {
        try {
            ProductUnitConversionResponseDto updatedUnitConversion = productUnitConversionService.updateUnitConversion(requestDto);
            return ResponseEntity.ok(updatedUnitConversion);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{uuid}")
    @ApiOperation(value = "Delete Product Unit Conversion", notes = "Delete an existing product unit conversion.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product Unit Conversion deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Void> deleteProductUnitConversion(@PathVariable String uuid) {
        try {
            productUnitConversionService.deleteUnitConversion(uuid);
            return ResponseEntity.noContent().build();
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/product-unit-list/{productUuid}")
    @ApiOperation(value = "Get Product Unit Conversions by Product UUID", notes = "Get all product unit conversions for a product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product unit conversions"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Map<String, Object>> getProductUnitConversionsByProduct(
            @PathVariable String productUuid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uuid,asc") String[] sort) {
        try {
            Map<String, Object> response = productUnitConversionService.getByProduct(productUuid, page, size, sort);
            return ResponseEntity.ok(response);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
