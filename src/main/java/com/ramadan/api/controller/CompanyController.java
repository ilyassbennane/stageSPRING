package com.ramadan.api.controller;

import com.ramadan.api.dto.company.model.CompanyRequestDto;

import com.ramadan.api.dto.company.model.CompanyResponseDto;
import com.ramadan.api.dto.company.model.CompanyUpdateDto;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiError;
import com.ramadan.api.services.company.ICompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(
   name = "Company Management Controller",
   description = "APIs - Create Company, Update Company, Get Company, Get All Companies, Delete Company, Search Companies"
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Authentication is required to access the resource.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        }),
		@ApiResponse(responseCode = "400", description = "The syntax of the request is incorrect.", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }),
		@ApiResponse(responseCode = "404", description = "Resource not found.", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }),
		@ApiResponse(responseCode = "500", description = "A system error occurred.", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }),
		@ApiResponse(responseCode = "501", description = "Requested functionality is not supported by the server.", content = {
				@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
})
@RequestMapping({"${URL-BASE}/company"})
public class CompanyController {
   @Autowired
   ICompanyService companyService;

   public CompanyController() {
   }

   @Operation(
      summary = "Save Company",
      description = "Create Company REST API is used to save Company in a database"
   )
   @ApiResponses({
	    @ApiResponse(responseCode = "200", description = "Save the Company", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompanyResponseDto.class))),
	    @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json"))
	})
   @PostMapping({"create"})
   public ResponseEntity<CompanyResponseDto> createCompany(@Valid  @RequestBody CompanyRequestDto companyRequestDto) throws APIErrorException {
      CompanyResponseDto createdCompany = this.companyService.createCompany(companyRequestDto);
      return ResponseEntity.ok(createdCompany);
   }

   @Operation(
      summary = "Get List of Companies",
      description = "Get All Companies REST API is used to get all the Companies from the database"
   )
   @ApiResponses({@ApiResponse(
      responseCode = "200",
      description = "Found the Companies",
      content = {@Content(
         mediaType = "application/json",
         schema = @Schema(
         implementation = CompanyResponseDto.class
      )
   )}
   ), @ApiResponse(
      responseCode = "404",
      description = "Companies not found",
      content = {@Content}
   )})
   @GetMapping({"company-list"})
   public ResponseEntity<Map<String, Object>> search(@RequestParam(required = false) String name, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "id,desc") String[] sort) throws APIErrorException {
      Map<String, Object> lCompanysMap = this.companyService.getAllCompanies(name, page, size, sort);
      return new ResponseEntity<>(lCompanysMap, HttpStatus.OK);
   }

   @Operation(
      summary = "Get Company by its uuid",
      description = "Get Company By ID REST API is used to get a single Company from the database"
   )
   @ApiResponses({@ApiResponse(
      responseCode = "200",
      description = "Found the Company",
      content = {@Content(
         mediaType = "application/json",
         schema = @Schema(
         implementation = CompanyResponseDto.class
      )
   )}
   ), @ApiResponse(
      responseCode = "400",
      description = "Invalid id supplied",
      content = {@Content}
   ), @ApiResponse(
      responseCode = "404",
      description = "Company not found",
      content = {@Content}
   )})
   @GetMapping({"/{CompanyUuid}"})
   public ResponseEntity<CompanyResponseDto> getCompanyById(@PathVariable("CompanyUuid") String CompanyUuid) throws APIErrorException {
      CompanyResponseDto oCompany = this.companyService.getCompanyByUuid(CompanyUuid);
      return ResponseEntity.ok(oCompany);
   }

   @Operation(
      summary = "Update Company by its uuid",
      description = "Update Company REST API is used to update a particular Company in the database"
   )
   @ApiResponses({@ApiResponse(
      responseCode = "200",
      description = "Company Updated",
      content = {@Content(
         mediaType = "application/json",
         schema = @Schema(
         implementation = CompanyResponseDto.class
      )
   )}
   ), @ApiResponse(
      responseCode = "400",
      description = "Invalid id supplied",
      content = {@Content}
   ), @ApiResponse(
      responseCode = "404",
      description = "Company not found",
      content = {@Content}
   )})
   @PutMapping({""})
   public ResponseEntity<CompanyResponseDto> updateCompany(@Valid @RequestBody CompanyUpdateDto updatedCompany) throws APIErrorException {
      CompanyResponseDto oCompany = this.companyService.updateCompany(updatedCompany);
      return ResponseEntity.ok(oCompany);
   }

   @Operation(
      summary = "Delete Company by its uuid",
      description = "Delete Company REST API is used to delete a particular Company from the database"
   )
   @ApiResponses({@ApiResponse(
      responseCode = "204",
      description = "Company Deleted",
      content = {@Content(
         mediaType = "application/json",
         schema = @Schema(
         implementation = CompanyResponseDto.class
      )
   )}
   ), @ApiResponse(
      responseCode = "400",
      description = "Invalid id supplied",
      content = {@Content}
   ), @ApiResponse(
      responseCode = "404",
      description = "Company not found",
      content = {@Content}
   )})
   @DeleteMapping({"{CompanyUuid}"})
   public ResponseEntity<Void> deleteCompany(@PathVariable("CompanyUuid") String CompanyUuid) throws APIErrorException {
      this.companyService.deleteCompany(CompanyUuid);
      return ResponseEntity.noContent().build();
   }
}
