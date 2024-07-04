package com.ramadan.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ramadan.api.dto.familyCostumer.model.FamilyCostumerDto;
import com.ramadan.api.dto.familyCostumer.model.FamilyCostumerRequestDto;
import com.ramadan.api.dto.familyCostumer.model.FamilyCostumerResponseDto;
import com.ramadan.api.dto.familyCostumer.model.FamilyCostumerSearchRequestDto;
import com.ramadan.api.dto.familyCostumer.model.FamilyCostumerUpdateDto;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiError;
import com.ramadan.api.services.costumer.familyCostumer.IFamilyCostumerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Family Costumers Management Controller", description = "APIs- Family Costumers")
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
@RequestMapping(path = "${URL-BASE}/family-costumer", produces = MediaType.APPLICATION_JSON_VALUE)
public class FamilyCostumerController {

	@Autowired
	private IFamilyCostumerService familyCostumerService;

    @Operation(summary = "Get FamilyCostumerD by its uuid", description = "Get FamilyCostumer By ID REST API is used to get a single FamilyCostumerDto from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the FamilyCostumerDto", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FamilyCostumerDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "FamilyCostumerDto not found", content = @Content) })
   @GetMapping("/{uuid}")
	public ResponseEntity<FamilyCostumerDto> getFamilyCostumerByUuid(@PathVariable String uuid)
			throws APIErrorException {

		return ResponseEntity.ok(familyCostumerService.getFamilyCostumerByUuid(uuid));

	}


    @Operation(summary = "Save FamilyCostumer", description = "Create FamilyCostumer REST API is used to save FamilyCostumer in a database")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Save the Company", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = FamilyCostumerResponseDto.class)) }), })
    @PostMapping("create")

	public ResponseEntity<FamilyCostumerResponseDto> createFamilyCostumer(
			@Valid	@RequestBody FamilyCostumerRequestDto familyCostumerRequestDto) throws APIErrorException {

		FamilyCostumerResponseDto createdFamilyCostumer = familyCostumerService
				.saveFamilyCostumer(familyCostumerRequestDto);
		return ResponseEntity.ok(createdFamilyCostumer);

	}

    @Operation(summary = "Update FamilyCostumer by its uuid", description = "Update FamilyCostumer REST API is used to update a particular FamilyCostumer in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FamilyCostumer Updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FamilyCostumerResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "FamilyCostumer not found", content = @Content) })
   @PutMapping
	public ResponseEntity<FamilyCostumerResponseDto> updateFamilyCostumer(
			@RequestBody FamilyCostumerUpdateDto familyCostumerUpdateDto) throws APIErrorException {

		FamilyCostumerResponseDto updatedFamilyCostumer = familyCostumerService
				.updateFamilyCostumer(familyCostumerUpdateDto);
		return ResponseEntity.ok(updatedFamilyCostumer);

	}


//	@Operation(summary = "Get all family costumers with pagination", description = "Get a paginated list of all family costumers.")
//	@ApiResponses(value = {
//			@ApiResponse(code = 200, message = "Successfully retrieved family costumers", response = FamilyCostumerResponseDto.class, responseContainer = "List"),
//			@ApiResponse(code = 404, message = "Family costumers not found") })
//	@GetMapping("/all")
//	public ResponseEntity<List<FamilyCostumerResponseDto>> getAllFamilyCostumersWithPagination(
//			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
//			@RequestParam(defaultValue = "uuid,desc") String[] sort) throws APIErrorException {
//
//	
//		List<FamilyCostumerResponseDto> response = familyCostumerService.getAllFamilyCostumer();
//		return ResponseEntity.ok(response);
//
//	}


	@Operation(summary = "Get all family costumers by company with pagination", description = "Get a paginated list of all family costumers by company.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FamilyCostumer Updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FamilyCostumerResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "FamilyCostumer not found", content = @Content) })
	@GetMapping("/familys-company-list/{companyUuid}")
	public ResponseEntity<Map<String, Object>> getAllFamilyCostumersByCompanyWithPagination(
			@PathVariable String companyUuid, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "uuid,desc") String[] sort)
			throws APIErrorException {

		Map<String, Object> response = familyCostumerService.findAllByCompany(companyUuid, page, size, sort);
		return ResponseEntity.ok(response);

	}

	@Operation(summary = "Get all family costumers by parent with pagination", description = "Get a paginated list of all family costumers by parent.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the FamilyCostumer", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FamilyCostumerResponseDto.class)) }),
            @ApiResponse(responseCode = "404", description = "FamilyCostumer not found", content = @Content) })
	@GetMapping("/family-parent-list/{parentUuid}")
	public ResponseEntity<Map<String, Object>> getAllFamilyCostumersByParentWithPagination(
			@PathVariable String parentUuid, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "uuid,desc") String[] sort)
			throws APIErrorException {

		Map<String, Object> response = familyCostumerService.findAllByParent(parentUuid, page, size, sort);
		return ResponseEntity.ok(response);

	}

	@Operation(summary = "Delete a family costumer by UUID", description = "Delete a family costumer by providing its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company Deleted", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FamilyCostumerDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Company not found", content = @Content) })
   @DeleteMapping("/{uuid}")
	public ResponseEntity<Void> deleteFamilyCostumer(@PathVariable String uuid) throws APIErrorException {

		familyCostumerService.deleteFamilyCostumer(uuid);
		return ResponseEntity.noContent().build();

	}
	
    /**********************************************
     * Get list FamilyCostumer
     * @throws APIErrorException
     ******************************************/


    @Operation(summary = "Search of FamilyCostumer", description = "Get All FamilyCostumer REST API is used to get all the FamilyCostumer from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the FamilyCostumer", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FamilyCostumerResponseDto.class)) }),
            @ApiResponse(responseCode = "404", description = "FamilyCostumer not found", content = @Content) })


    @PostMapping("search")
    public ResponseEntity<Map<String, Object>> search(@Valid @RequestBody(required = false) FamilyCostumerSearchRequestDto searchRequestDto,
                                                      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size,
                                                      @RequestParam(defaultValue = "id,desc") String[] sort)
            throws APIErrorException {

        Map<String, Object> lFamilyCostumersMap = familyCostumerService.search(searchRequestDto, page, size, sort);

        return new ResponseEntity<>(lFamilyCostumersMap, HttpStatus.OK);
    }
}
