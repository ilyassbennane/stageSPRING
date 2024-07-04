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

import com.ramadan.api.dto.agance.model.AddressAgencyRequestDto;
import com.ramadan.api.dto.agance.model.AgenceResponseDto;
import com.ramadan.api.dto.agance.model.AgenceUpdateDto;
import com.ramadan.api.dto.company.model.CompanyResponseDto;
import com.ramadan.api.dto.costumer.model.AddressCostumerRequestDto;
import com.ramadan.api.dto.costumer.model.AddressCostumerResponseDto;
import com.ramadan.api.dto.costumer.model.AddressCostumerUpdateListDto;
import com.ramadan.api.dto.costumer.model.CostumerDto;
import com.ramadan.api.dto.costumer.model.CostumerFilterRequestDto;
import com.ramadan.api.dto.costumer.model.CostumerRequestDto;
import com.ramadan.api.dto.costumer.model.CostumerUpdateDto;
import com.ramadan.api.dto.costumer.model.EmailCostumerRequestDto;
import com.ramadan.api.dto.costumer.model.EmailCostumerResponseDto;
import com.ramadan.api.dto.costumer.model.EmailCostumerUpdateListDto;
import com.ramadan.api.dto.costumer.model.PhoneCostumerRequestDto;
import com.ramadan.api.dto.costumer.model.PhoneCostumerResponseDto;
import com.ramadan.api.dto.costumer.model.PhoneCostumerUpdateListDto;
import com.ramadan.api.entity.agence.AdresseAgency;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiError;
import com.ramadan.api.services.costumer.CostumerService;
import com.ramadan.api.services.costumer.ICostumerService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Costumer Management Controller", description = "APIs")
@RequestMapping({ "${URL-BASE}/costumer" })

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
public class CostumerController {

	@Autowired
	ICostumerService customerService;

	public CostumerController() {
	}

	@Operation(summary = "Save Costumer", description = "Create Costumer REST API is used to save Costumer in a database")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Create the Costumer", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompanyResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json")) })
	@PostMapping({ "create" })
	public ResponseEntity<CostumerDto> createCostumer(@Valid @RequestBody List<CostumerRequestDto>  customerRequestDto)
			throws APIErrorException {
		this.customerService.saveCostumer(customerRequestDto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Get Costumer by its uuid", description = "Get Costumer By ID REST API is used to get a single Costumer from the database")
	@ApiOperation(value = "Get a Costumer by UUID", notes = "Get Costumer details by providing its UUID.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Retrieved the costumer", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompanyResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json")) })
	@GetMapping("/{uuid}")
	public ResponseEntity<CostumerDto> getCostumerByUuid(@PathVariable String uuid) throws APIErrorException {
		return ResponseEntity.ok(customerService.findByUuid(uuid));
	}

    /**********************************************
     * Get list Costumer
     * @throws APIErrorException
     ******************************************/
    @Operation(summary = "Get List Costumers", description = "Get All Costumers REST API is used to get all the Costumers from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Costumers", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CostumerDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Costumers not found", content = @Content) })
    @PostMapping("search")
    public ResponseEntity<Map<String, Object>> search(@Valid @RequestBody(required = false) CostumerFilterRequestDto searchRequestDto,
                                                      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size,
                                                      @RequestParam(defaultValue = "id,desc") String[] sort)
            throws APIErrorException {

        Map<String, Object> lCostumersMap = customerService.search(searchRequestDto, page, size, sort);

        return new ResponseEntity<>(lCostumersMap, HttpStatus.OK);
    }

	@Operation(summary = "Update Costumer by its uuid", description = "Update Costumer REST API is used to update a particular Costumer in the database")
	@ApiOperation(value = "Update an existing Costumer", notes = "Update an existing Costumer with provided UUID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Costumer updated successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = AgenceUpdateDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)) }), })
	@PutMapping
	public ResponseEntity<CostumerDto> updateAgence(@Valid @RequestBody CostumerUpdateDto costumerUpdate)
			throws APIErrorException {
		return ResponseEntity.ok(customerService.updateEntity(costumerUpdate.getUuid(), costumerUpdate));
	}

	@Operation(summary = "Delete Costumer by its uuid", description = "Delete Costumer REST API is used to delete a particular Costumer from the database")
	@ApiOperation(value = "Delete a Costumer by UUID", notes = "Delete a Costumer by providing its UUID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Costumer deleted successfully"), })
	@DeleteMapping("/{uuid}")
	public ResponseEntity<Void> deleteCostumer(@PathVariable String uuid) throws APIErrorException {
		customerService.deleteCostumer(uuid);
		return ResponseEntity.noContent().build();
	}
	///////////////// Address APIS

	@Operation(summary = "Add Addresse to a Costumer", description = "Add Addresse Costumer REST API to add an address to a costumer")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Address added successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AddressAgencyRequestDto.class)) }) })
	@PostMapping("/{uuid}/addresses")
	public ResponseEntity<CostumerDto> addAddressToCostumer(@PathVariable String uuid,
			@RequestBody AddressCostumerRequestDto addressDto) throws APIErrorException {
		CostumerDto responseDto = customerService.addAddressToCostumer(uuid, addressDto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Set Main Address", description = "Main Address REST API to Set Main Address")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Main Set successfully") })
	@PostMapping("/addresses/{uuid}/set-main")
	public ResponseEntity<CostumerDto> setMainAddress(@PathVariable String uuid) throws APIErrorException {
		CostumerDto responseDto = customerService.setMainAddress(uuid);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Get Addresses Costumer", description = "Address Customer REST API to Get addresses of a Costumer")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "success", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AdresseAgency.class)) }) })

	@GetMapping("/{uuid}/addresses")
	public ResponseEntity<List<AddressCostumerResponseDto>> getAllAddressCostumer(@PathVariable String uuid)
			throws APIErrorException {
		List<AddressCostumerResponseDto> addressCostumer = customerService.getAllAddressCostumers(uuid);
		return ResponseEntity.ok(addressCostumer);
	}

	//////////////////////////////////////////////
	/////////// PhoneAPIS

	@Operation(summary = "Add Phone Number to a Costumer", description = "Add Phone Costumer REST API to add phone to a costumer")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "phone added successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AddressAgencyRequestDto.class)) }) })
	@PostMapping("/{uuid}/phones")
	public ResponseEntity<CostumerDto> addPhoneToCostumer(@PathVariable String uuid,
			@RequestBody PhoneCostumerRequestDto phoneDto) throws APIErrorException {
		CostumerDto responseDto = customerService.addPhoneToCostumer(uuid, phoneDto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Set Main Phone", description = "Main Phone REST API to Set Main Phone")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Main Set successfully") })
	@PostMapping("/phones/{uuid}/set-main")
	public ResponseEntity<CostumerDto> setMainPhone(@PathVariable String uuid) throws APIErrorException {
		CostumerDto responseDto = customerService.setMainPhone(uuid);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Get Phones Costumer", description = "Phone Customer REST API to Get phones of a Costumer")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "success", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AdresseAgency.class)) }) })

	@GetMapping("/{uuid}/phones")
	public ResponseEntity<List<PhoneCostumerResponseDto>> getAllPhonesCostumer(@PathVariable String uuid)
			throws APIErrorException {
		List<PhoneCostumerResponseDto> phoneCostumer = customerService.getAllPhonesCostumer(uuid);
		return ResponseEntity.ok(phoneCostumer);
	}

	///////////////////////////////////
	/////////////////// EmailApis

	@Operation(summary = "Add Email to a Costumer", description = "Add Email REST API to add email to a costumer")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "emails added successfully", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AddressAgencyRequestDto.class)) }) })
	@PostMapping("/{uuid}/emails")
	public ResponseEntity<CostumerDto> addEmailsToCostumer(@PathVariable String uuid,
			@RequestBody EmailCostumerRequestDto emailDto) throws APIErrorException {
		CostumerDto responseDto = customerService.addEmailToCostumer(uuid, emailDto);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Set Main Emails", description = "Main Email REST API to Set Main Email")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Main Set successfully") })
	@PostMapping("/emails/{uuid}/set-main")
	public ResponseEntity<CostumerDto> setMainEmails(@PathVariable String uuid) throws APIErrorException {
		CostumerDto responseDto = customerService.setMainEmail(uuid);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Get Emails Costumer", description = "Email Customer REST API to Get emails of a Costumer")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "success", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AdresseAgency.class)) }) })

	@GetMapping("/{uuid}/emails")
	public ResponseEntity<List<EmailCostumerResponseDto>> getAllEmailsCostumer(@PathVariable String uuid)
			throws APIErrorException {
		List<EmailCostumerResponseDto> emailCostumer = customerService.getAllEmailsCostumer(uuid);
		return ResponseEntity.ok(emailCostumer);
	}
	
	
	
	
	//////UPDATE
    @PutMapping("/update/phones")
    @Operation(summary = "Update Phones for Costumer", description = "Update phone numbers associated with a customer")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Phones updated successfully", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> updatePhonesForCostumer(@RequestBody PhoneCostumerUpdateListDto phoneUpdateListDto) throws APIErrorException {
        customerService.updatePhonesForCostumer(phoneUpdateListDto);
        return ResponseEntity.ok().build();
    }

    // API to update addresses for a customer
    @PutMapping("/update/addresses")
    @Operation(summary = "Update Addresses for Costumer", description = "Update addresses associated with a customer")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Addresses updated successfully", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> updateAddressesForCostumer(@RequestBody AddressCostumerUpdateListDto addressUpdateListDto) throws APIErrorException {
        customerService.updateAddressesForCostumer(addressUpdateListDto);
        return ResponseEntity.ok().build();
    }

    // API to update emails for a customer
    @PutMapping("/update/emails")
    @Operation(summary = "Update Emails for Costumer", description = "Update email addresses associated with a customer")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Emails updated successfully", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> updateEmailsForCostumer(@RequestBody EmailCostumerUpdateListDto emailUpdateListDto) throws APIErrorException {
        customerService.updateEmailsForCostumer(emailUpdateListDto);
        return ResponseEntity.ok().build();
    }
    
    
    
    
    
    
    /////////Delete
    
    
 // Delete multiple phones
    @DeleteMapping("/delete/phones")
    @Operation(summary = "Delete Multiple Phones", description = "Delete multiple phone numbers by UUIDs")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Phones deleted successfully", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> deletePhonesForCostumer(@RequestBody List<String> phoneUuids) throws APIErrorException {
        customerService.deletePhonesForCostumer(phoneUuids);
        return ResponseEntity.ok().build();
    }

    // Delete multiple addresses
    @DeleteMapping("/delete/addresses")
    @Operation(summary = "Delete Multiple Addresses", description = "Delete multiple addresses by UUIDs")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Addresses deleted successfully", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> deleteAddressesForCostumer(@RequestBody List<String> addressUuids) throws APIErrorException {
        customerService.deleteAddressesForCostumer(addressUuids);
        return ResponseEntity.ok().build();
    }

    // Delete multiple emails
    @DeleteMapping("/delete/emails")
    @Operation(summary = "Delete Multiple Emails", description = "Delete multiple emails by UUIDs")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Emails deleted successfully", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Void> deleteEmailsForCostumer(@RequestBody List<String> emailUuids) throws APIErrorException {
        customerService.deleteEmailsForCostumer(emailUuids);
        return ResponseEntity.ok().build();
    }


       
    
    

}
