package com.ramadan.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ramadan.api.dto.agance.model.AddressAgencyRequestDto;
import com.ramadan.api.dto.agance.model.AddressAgencyResponseDto;
import com.ramadan.api.dto.agance.model.AgenceRequestDto;
import com.ramadan.api.dto.agance.model.AgenceResponseDto;
import com.ramadan.api.dto.agance.model.AgenceUpdateDto;
import com.ramadan.api.dto.depot.model.AddressDepositRequestDto;
import com.ramadan.api.dto.depot.model.AddressDepositResponseDto;
import com.ramadan.api.dto.depot.model.DepotRequestDto;
import com.ramadan.api.dto.depot.model.DepotResponseDto;
import com.ramadan.api.dto.depot.model.DepotUpdateDto;
import com.ramadan.api.entity.agence.AdresseAgency;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiError;
import com.ramadan.api.services.agence.IAgenceService;
import com.ramadan.api.services.stock.deposit.IDepositService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@ApiResponses(value = {
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
@RequestMapping("${URL-BASE}/deposit")
@Tag(name = "Deposit Management Controller", description = "APIs - Create Deposit, Update Deposit, Get Deposit, Get All Deposits, Delete Deposit, Deposits-Company-list")
public class DepositController {
	@Autowired
	IDepositService depositService;
	@Operation(summary = "Save Depot", description = "Create Depot REST API to save an Depot with a main address")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Depot created successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AgenceResponseDto.class)) }) })
	@PostMapping("/create")
	public ResponseEntity<DepotResponseDto> createAgence(@Valid @RequestBody DepotRequestDto depotRequest)
			throws APIErrorException {
		DepotResponseDto depotResponseDto = depositService.createDeposit(depotRequest);
		return ResponseEntity.ok(depotResponseDto);
	}

	@Operation(summary = "Add Addresses to a Depot", description = "Add Addresses Depot REST API to add an address to a depot")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Address added successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AddressAgencyRequestDto.class)) }) })
	@PostMapping("/{uuid}/addresses")
	public ResponseEntity<DepotResponseDto> addAddressToDepot(@PathVariable String uuid,
			@RequestBody AddressDepositRequestDto addressDto) throws APIErrorException {
		DepotResponseDto responseDto = depositService.addAddressToDepot(uuid, addressDto);
		return ResponseEntity.ok(responseDto);
	}
	
	
	
	
	

	@Operation(summary = "Set Main Address", description = "Main Address REST API to Set Main Address")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Main Set successfully") })
	@PostMapping("/addresses/{uuid}/set-main")
	public ResponseEntity<DepotResponseDto> setMainAddress(@PathVariable String uuid) throws APIErrorException {
		DepotResponseDto responseDto = depositService.setMainAddress(uuid);
		return ResponseEntity.ok(responseDto);
	}

	@Operation(summary = "Get Addresses Deposit", description = "Address Deposit REST API to Get addresses of an deposit")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "success", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AdresseAgency.class)) }) })

	@GetMapping("/{uuid}/addresses")
	public ResponseEntity<List<AddressDepositResponseDto>> getAllAddressAgencies(@PathVariable String uuid)
			throws APIErrorException {
		List<AddressDepositResponseDto> addressDepot = depositService.getAllAddressDepot(uuid);
		return ResponseEntity.ok(addressDepot);
	}
	

	@Operation(summary = "Update Deposit by its uuid", description = "Update Deposit REST API is used to update a particular Deposit in the database")
	@ApiOperation(value = "Update an existing Deposit", notes = "Update an existing Deposit with provided UUID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Deposit updated successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AgenceUpdateDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }), })
	@PutMapping
	public ResponseEntity<DepotResponseDto> updateAgence(@Valid @RequestBody DepotUpdateDto depositUpdate)
			throws APIErrorException {
		return ResponseEntity.ok(depositService.updateEntity(depositUpdate.getUuid(), depositUpdate));
	}
	

	@Operation(summary = "Delete Deposit by its uuid", description = "Delete Deposit REST API is used to delete a particular Deposit from the database")
	@ApiOperation(value = "Delete a Deposit by UUID", notes = "Delete a Deposit by providing its UUID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Deposit deleted successfully"), })
	@DeleteMapping("/{uuid}")
	public ResponseEntity<Void> deleteAgence(@PathVariable String uuid) throws APIErrorException {
		depositService.deleteDeposit(uuid);
		return ResponseEntity.noContent().build();
	}

}
