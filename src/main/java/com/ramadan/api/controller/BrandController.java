package com.ramadan.api.controller;

import com.ramadan.api.dto.brand.BrandDto;
import com.ramadan.api.dto.brand.BrandRequestDto;
import com.ramadan.api.dto.brand.BrandResponseDto;
import com.ramadan.api.dto.brand.BrandSearchRequestDto;
import com.ramadan.api.dto.brand.BrandUpdateDto;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiError;
import com.ramadan.api.services.brand.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;

@RestController
@Tag(name = "Brands Management Controller", description = "APIs - Create Brand, Update Brand, Get Brand, Get All Brands, Delete Brand")
@ApiResponses({
        @ApiResponse(responseCode = "401", description = "Authentication is required to access the resource.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }),
        @ApiResponse(responseCode = "400", description = "The syntax of the request is incorrect.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }),
        @ApiResponse(responseCode = "404", description = "Resource not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }),
        @ApiResponse(responseCode = "500", description = "A system error occurred.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }),
        @ApiResponse(responseCode = "501", description = "Requested functionality is not supported by the server.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }) })

@RequestMapping(path = "${URL-BASE}/brands")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    @GetMapping("/{uuid}")
    @Operation(summary = "Get Brand by UUID", description = "Get brand details by providing its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved brand"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<BrandDto> getBrandByUuid(@PathVariable String uuid) throws APIErrorException  {
        
            BrandDto brandDto = brandService.getBrandByUuid(uuid);
            return ResponseEntity.ok(brandDto);
  
    }

    @GetMapping("/brands-company-list/{companyUuid}")
    @Operation(summary = "Get All Brands by Company UUID", description = "Get all brands for a company.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved brands"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Map<String, Object>> getAllBrandsByCompany (
            @PathVariable String companyUuid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uuid,asc") String[] sort)throws APIErrorException  {
        
            Map<String, Object> response = brandService.getAllByCompany(companyUuid, page, size, sort);
            return ResponseEntity.ok(response);
        
    }

    @PostMapping
    @Operation(summary = "Create Brand", description = "Create a new brand.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Brand created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<BrandResponseDto> createBrand(@Valid @RequestBody BrandRequestDto brandRequestDto) throws APIErrorException {
     
            BrandResponseDto createdBrand = brandService.saveBrand(brandRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBrand);
     
    }

    @PutMapping()
    @Operation(summary = "Update Brand", description = "Update an existing brand.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand updated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<BrandResponseDto> updateBrand( @Valid @RequestBody BrandUpdateDto brandRequestDto) throws APIErrorException {
       
            BrandResponseDto updatedBrand = brandService.updateBrand(brandRequestDto);
            return ResponseEntity.ok(updatedBrand);
         
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete Brand", description = "Delete an existing brand.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Brand deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Void> deleteBrand(@PathVariable String uuid) throws APIErrorException {
        
            brandService.deleteBrand(uuid);
            return ResponseEntity.noContent().build();
       
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Brands", description = "Get all brands with pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved brands"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Map<String, Object>> getAllBrands (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uuid,asc") String[] sort) throws APIErrorException {
        
            Map<String, Object> response = brandService.getAllBrands(page, size, sort);
            return ResponseEntity.ok(response);
       
    }
    
    @Operation(summary = "Search of Brand", description = "Search for all brands REST API is used to get all the Brand from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Brand", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BrandResponseDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Brand not found", content = @Content) })
    @PostMapping("search")
    public ResponseEntity<Map<String, Object>> search(@Valid @RequestBody(required = false) BrandSearchRequestDto searchRequestDto,
                                                      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size,
                                                      @RequestParam(defaultValue = "id,desc") String[] sort)
            throws APIErrorException {

        Map<String, Object> lBrandsMap = brandService.search(searchRequestDto, page, size, sort);

        return new ResponseEntity<>(lBrandsMap, HttpStatus.OK);
    }
}
