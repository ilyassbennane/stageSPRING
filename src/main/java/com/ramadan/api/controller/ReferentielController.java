package com.ramadan.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ramadan.api.dto.referentiel.CiviliteDto;
import com.ramadan.api.dto.referentiel.NationnaliteDto;
import com.ramadan.api.dto.referentiel.PaysDto;
import com.ramadan.api.dto.referentiel.VilleDto;
import com.ramadan.api.exceptions.ApiError;
import com.ramadan.api.services.referentiel.IReferentielService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("${URL-BASE}/referentiel")
@Tag(name = "Referentiel", description = " APIs - Contains All Referentiel Endpoint")
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
@RestController
public class ReferentielController {

	@Autowired
	IReferentielService referentielService;
	
	
	@GetMapping("civilites")
	@Operation(summary = "Get List Civilite", description = "Return List of All Civilite from data base", method = "getAllCivilite", tags = {})
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Data Found", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = CiviliteDto.class)),})})
	public ResponseEntity<List<CiviliteDto>> getAllCivilite(){
		
		List<CiviliteDto> civiliteDtos = referentielService.AllCivilite();
		return new ResponseEntity<>(civiliteDtos, HttpStatus.OK);
	}
	
	
	@GetMapping("nationnalites")
	@Operation(summary = "Get List nationnalite", description = "Return List of All nationnalite from data base", method = "getAllnationnalite", tags = {})
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Data Found", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = NationnaliteDto.class)),})})
	public ResponseEntity<Map<String, Object>> getAllnationnalite(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "uuid,desc") String[] sort){
		
		Map<String, Object> nationnaliteDtos = referentielService.AllNationnalite(page, size, sort);
		return new ResponseEntity<>(nationnaliteDtos, HttpStatus.OK);
	}
	
	@GetMapping("villes")
	@Operation(summary = "Get List villes ", description = "Return List of All villes  from data base", method = "getAllVilles", tags = {})
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Data Found", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class)),})})
	public ResponseEntity<List<VilleDto>> getAllVilles(){
		
		List<VilleDto> villeDtos = referentielService.AllVille();
		return new ResponseEntity<>(villeDtos, HttpStatus.OK);
	}
	
	@GetMapping("pays")
	@Operation(summary = "Get List Pays ", description = "Return List of All pays  from data base", method = "getAllPays", tags = {})
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Data Found", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = PaysDto.class)),})})
	public ResponseEntity<List<PaysDto>> getAllPays(){
		
		List<PaysDto> paysDtos = referentielService.AllPays();
		return new ResponseEntity<>(paysDtos, HttpStatus.OK);
	}
	
}
