package com.ramadan.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ramadan.api.dto.agance.model.AgenceRequestDto;
import com.ramadan.api.dto.agance.model.AgenceResponseDto;
import com.ramadan.api.dto.depot.model.DepotRequestDto;
import com.ramadan.api.dto.depot.model.DepotResponseDto;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiError;
import com.ramadan.api.services.agence.IAgenceService;
import com.ramadan.api.services.stock.deposit.IDepositService;

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
	@Operation(summary = "Save Agency", description = "Create Agency REST API to save an Agency with a main address")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Agency created successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AgenceResponseDto.class)) }) })
	@PostMapping("/create")
	public ResponseEntity<DepotResponseDto> createAgence(@Valid @RequestBody DepotRequestDto depotRequest)
			throws APIErrorException {
		DepotResponseDto depotResponseDto = depositService.createDeposit(depotRequest);
		return ResponseEntity.ok(depotResponseDto);
	}

}
