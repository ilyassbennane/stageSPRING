package com.ramadan.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.ramadan.api.dto.IMapClassWithDto;
import com.ramadan.api.dto.agance.model.AddressAgencyRequestDto;
import com.ramadan.api.dto.agance.model.AddressAgencyResponseDto;
import com.ramadan.api.dto.agance.model.AddressAgencyUpdateListDto;
import com.ramadan.api.dto.agance.model.AgenceFilterRequestDto;
import com.ramadan.api.dto.agance.model.AgenceRequestDto;
import com.ramadan.api.dto.agance.model.AgenceResponseDto;
import com.ramadan.api.dto.agance.model.AgenceUpdateDto;
import com.ramadan.api.dto.agance.model.PhoneAgencyRequestDto;
import com.ramadan.api.dto.agance.model.PhoneAgencyResponseDto;
import com.ramadan.api.dto.agance.model.PhoneAgencyUpdateListDto;
import com.ramadan.api.dto.costumer.model.CostumerDto;
import com.ramadan.api.dto.costumer.model.CostumerFilterRequestDto;
import com.ramadan.api.entity.agence.AdresseAgency;
import com.ramadan.api.entity.agence.Agency;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiError;
import com.ramadan.api.services.agence.IAgenceService;

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
@RequestMapping("${URL-BASE}/agency")
@Tag(name = "Agency Management Controller", description = "APIs - Create Agency, Update Agency, Get Agency, Get All Agencies, Delete Agency, Agencies-Company-list, UpdateAgencyCompany")
public class AgencyController {

	@Autowired
	IMapClassWithDto<Agency, AgenceResponseDto> agenseresponsemapper;
	@Autowired
	IAgenceService agenceService;

	@Operation(summary = "Save Agency", description = "Create Agency REST API to save an Agency with a main address")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Agency created successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AgenceResponseDto.class)) }) })
	@PostMapping("/create")
	public ResponseEntity<AgenceResponseDto> createAgence(@Valid @RequestBody AgenceRequestDto agenceRequest)
			throws APIErrorException {
		AgenceResponseDto agenceResponseDto = agenceService.saveAgence(agenceRequest);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Add Addresses to an Agency", description = "Add Addresses Agency REST API to add an address to an Agency")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Address added successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AddressAgencyRequestDto.class)) }) })
	@PostMapping("/{uuid}/addresses")
	public ResponseEntity<AgenceResponseDto> addAddressToAgence(@PathVariable String uuid,
			@RequestBody AddressAgencyRequestDto addressDto) throws APIErrorException {
		AgenceResponseDto responseDto = agenceService.addAddressToAgence(uuid, addressDto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Set Main Address", description = "Main Address REST API to Set Main Address")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Main Set successfully") })
	@PostMapping("/addresses/{uuid}/set-main")
	public ResponseEntity<AgenceResponseDto> setMainAddress(@PathVariable String uuid) throws APIErrorException {
		AgenceResponseDto responseDto = agenceService.setMainAddress(uuid);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Get Addresses Agency", description = "Address Agency REST API to Get addresses of an agency")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "success", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AdresseAgency.class)) }) })

	@GetMapping("/{uuid}/addresses")
	public ResponseEntity<List<AddressAgencyResponseDto>> getAllAddressAgencies(@PathVariable String uuid)
			throws APIErrorException {
		List<AddressAgencyResponseDto> addressAgencies = agenceService.getAllAddressAgencies(uuid);
		return ResponseEntity.ok(addressAgencies);
	}

	@Operation(summary = "Update Agency by its uuid", description = "Update Agency REST API is used to update a particular Agency in the database")
	@ApiOperation(value = "Update an existing agence", notes = "Update an existing agence with provided UUID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Agence updated successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AgenceUpdateDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }), })
	@PutMapping
	public ResponseEntity<AgenceResponseDto> updateAgence(@Valid @RequestBody AgenceUpdateDto agenceUpdate)
			throws APIErrorException {
		return ResponseEntity.ok(agenceService.updateEntity(agenceUpdate.getUuid(), agenceUpdate));
	}

	@Operation(summary = "Get Agency by its uuid", description = "Get Agency By ID REST API is used to get a single Agency from the database")
	@ApiOperation(value = "Get an agence by UUID", notes = "Get agence details by providing its UUID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved agence", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = AgenceResponseDto.class)) }),

	})
	@GetMapping("/{uuid}")
	public ResponseEntity<AgenceResponseDto> getAgenceByUuid(@PathVariable String uuid) throws APIErrorException {
		// Placeholder
		return ResponseEntity.ok(agenceService.findByUuid(uuid));
	}

	@Operation(summary = "Delete Agency by its uuid", description = "Delete Agency REST API is used to delete a particular Agency from the database")
	@ApiOperation(value = "Delete an agence by UUID", notes = "Delete an agence by providing its UUID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Agence deleted successfully"), })
	@DeleteMapping("/{uuid}")
	public ResponseEntity<Void> deleteAgence(@PathVariable String uuid) throws APIErrorException {
		agenceService.deleteAgence(uuid);
		return ResponseEntity.noContent().build();
	}


    /**********************************************
     * Get list Agence
     * @throws APIErrorException
     ******************************************/
    @Operation(summary = "Get List agences", description = "Get All Agences REST API is used to get all the Agences from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Agences", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CostumerDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Agences not found", content = @Content) })
    @PostMapping("search")
    public ResponseEntity<Map<String, Object>> search(@Valid @RequestBody(required = false) AgenceFilterRequestDto searchRequestDto,
                                                      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size,
                                                      @RequestParam(defaultValue = "id,desc") String[] sort)
            throws APIErrorException {

        Map<String, Object> lAgencesMap = agenceService.search(searchRequestDto, page, size, sort);

        return new ResponseEntity<>(lAgencesMap, HttpStatus.OK);
    }

	@Operation(summary = "Get List of Agencies of a Company", description = "Get List of agencies by the uuid of the Company from the database")
	@ApiOperation(value = "Get all agencies by company", notes = "Get all agencies for a company.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved all agencies for company"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error") })
	@GetMapping("/agencies-company-list/{uuid}")
	public ResponseEntity<Map<String, Object>> getAllAgenciesByCompany(@PathVariable String uuid,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "uuid,desc") String[] sort) throws APIErrorException {

		Map<String, Object> response = agenceService.findAllByCompany(uuid, page, size, sort);
		return ResponseEntity.ok(response);

	}

//@ApiOperation(value = "Save address for agency", notes = "Save address for agency.")
//@ApiResponses(value = {
//    @ApiResponse(responseCode = "200", description = "Successfully saved address for agency"),
//    @ApiResponse(responseCode = "500", description = "Internal Server Error")
//})
//@PostMapping("/adresse")
//public ResponseEntity<AddressResponseDto> saveAddressForAgency(@RequestBody AddressAgenceRequestDto requestDto) {
//try {
//    AddressResponseDto savedAddress = addressService.saveAddressAgency(requestDto);
//    return ResponseEntity.ok(savedAddress);
//} catch (APIErrorException e) {
//    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//}
//}

//@ApiOperation(value = "updateAgencyCompany ", notes = "Update the Company of the existing agency with provided AgencyUUID and CompanyUuid.")
//@ApiResponses(value = {
//      @ApiResponse(responseCode = "200", description = "Agence updated successfully", content = {
//              @Content(mediaType = "application/json", schema = @Schema(implementation = AgenceResponseDto.class))
//      }),
//      @ApiResponse(responseCode = "400", description = "Invalid input", content = {
//              @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
//      }),
//})
//@PutMapping("/updateAgencyCompany/{agencyUuid}/{newCompanyUuid}")
//public ResponseEntity<AgenceResponseDto> updateAgencyCompany(@PathVariable String agencyUuid,@PathVariable String newCompanyUuid) throws APIErrorException {
//  return ResponseEntity.ok(agenceService.updateAgencyCompany(agencyUuid,newCompanyUuid));
//}

//@ApiOperation(value = "Get all addresses by agency", notes = "Get all addresses for an agency.")
//@ApiResponses(value = {
//      @ApiResponse(responseCode = "200", description = "Successfully retrieved all addresses for agency"),
//      @ApiResponse(responseCode = "500", description = "Internal Server Error")
//})
//@GetMapping("/adresses-agency-list/{uuid}")
//public ResponseEntity<List<AddressResponseDto>> getAddressesByAgency(@PathVariable String uuid) {
//  try {
//      List<AddressResponseDto> addresses = addressService.findAllByAgency(uuid);
//      return ResponseEntity.ok(addresses);
//  } catch (APIErrorException e) {
//      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//  }
//}

	@Operation(summary = "Add Phone Number to a Agency", description = "Add Phone REST API to add phone to an agency")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "phone added successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AddressAgencyRequestDto.class)) }) })
	@PostMapping("/{uuid}/phones")
	public ResponseEntity<AgenceResponseDto> addPhoneToAgency(@PathVariable String uuid,
			@RequestBody PhoneAgencyRequestDto phoneDto) throws APIErrorException {
		AgenceResponseDto responseDto = agenceService.addPhoneToAgency(uuid, phoneDto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Set Main Phone", description = "Main Phone REST API to Set Main Phone")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Main Set successfully") })
	@PostMapping("/phones/{uuid}/set-main")
	public ResponseEntity<AgenceResponseDto> setMainPhone(@PathVariable String uuid) throws APIErrorException {
		AgenceResponseDto responseDto = agenceService.setMainPhone(uuid);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Get Phones Agence", description = "Phone Agence REST API to Get phones of an agency")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "success", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AdresseAgency.class)) }) })

	@GetMapping("/{uuid}/phones")
	public ResponseEntity<List<PhoneAgencyResponseDto>> getAllPhonesAgencies(@PathVariable String uuid)
			throws APIErrorException {
		List<PhoneAgencyResponseDto> phoneAgency = agenceService.getAllPhonesAgency(uuid);
		return ResponseEntity.ok(phoneAgency);
	}

	@Operation(summary = "Update Phone Numbers for an Agency", description = "Update Phone Numbers REST API to update the phone numbers of an agency")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Phone numbers updated successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = AgenceResponseDto.class)) }), })
	@PutMapping("/{uuid}/phones")
	public ResponseEntity<AgenceResponseDto> updatePhonesForAgency(@RequestBody PhoneAgencyUpdateListDto phoneDtos)
			throws APIErrorException {

		agenceService.updatePhonesForAgency(phoneDtos);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Update Addresses for an Agency", description = "Update Addresses REST API to update the Addresses of an agency")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Addresses updated successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = AgenceResponseDto.class)) }), })
	@PutMapping("/{uuid}/addresses")
	public ResponseEntity<AgenceResponseDto> updateAddressesForAgency(
			@RequestBody AddressAgencyUpdateListDto addressDtos) throws APIErrorException {

		agenceService.updateAddressesForAgency(addressDtos);
		return ResponseEntity.ok().build();
	}
}
