package com.ramadan.api.controller;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ramadan.api.dto.address.model.AddressDto;
import com.ramadan.api.dto.address.model.AddressUpdateDto;
import com.ramadan.api.dto.agance.model.AgenceResponseDto;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiError;
import com.ramadan.api.services.adress.AddressService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid; //add validators 

/**
 * Controller for handling address-related requests.
 * Provides endpoints for managing addresses, including retrieval, update, and deletion of address details.
 */
@RestController
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
@RequestMapping("${URL-BASE}/addresses")
@Tag(name = "Address Management Controller", description = "APIs -Get All Agencies")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * Retrieves the address details by UUID.
     * 
     * @param uuid The UUID of the address to retrieve.
     * @return ResponseEntity containing the address details or an error status.
     */
    @Operation(
  	      summary = "Get Address by its uuid",
  	      description = "Get Address By ID REST API is used to get a single Address from the database"
  	   )
  @ApiOperation(value = "Get an Address by UUID", notes = "Get Address details by providing its UUID.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved Address", content = {
                  @Content(mediaType = "application/json", schema = @Schema(implementation = AgenceResponseDto.class))
          }),

  })
    @GetMapping("/{uuid}")
    public ResponseEntity<AddressDto> getAddressByUuid(@PathVariable String uuid) {
        try {
            AddressDto address = addressService.findByUuid(uuid);
            return ResponseEntity.ok(address);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Updates the address details by UUID.
     * 
     * @param uuid The UUID of the address to update.
     * @param requestDto The address update information.
     * @return ResponseEntity containing the updated address details or an error status.
     */
    @Operation(
    	      summary = "Update Address by its uuid",
    	      description = "Update Address REST API is used to update a particular Address in the database"
    	   )
    @ApiOperation(value = "Update an existing Address", notes = "Update an existing Address with provided UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agence updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AgenceResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
            }),
    })
    @PutMapping("/{uuid}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable String uuid, @RequestBody AddressUpdateDto requestDto) {
        try {
            AddressDto updatedAddress = addressService.updateAddress(uuid, requestDto);
            return ResponseEntity.ok(updatedAddress);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves all addresses.
     * 
     * @return ResponseEntity containing a list of all addresses or an error status.
     */
    @Operation(
   	      summary = "Get List of Addresses",
   	      description = "Get All Addresses REST API is used to get all the Addresses from the database"
   	   )
    @ApiOperation(value = "Get all Addresses", notes = "Get a list of all Addresses.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved Addresses", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDto.class))
    }) 
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllAddresses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uuid,desc") String[] sort) throws APIErrorException  {
            Map<String, Object> response = addressService.findAll(page, size, sort);
            return ResponseEntity.ok(response);
       
    }

    
    @Operation(
    	      summary = "Delete Address by its uuid",
    	      description = "Delete Address REST API is used to delete a particular Address from the database"
    	   )
    @ApiOperation(value = "Delete an address by UUID", notes = "Delete an address by providing its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Address deleted successfully"),
    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteAddress(@PathVariable String uuid) {
        try {
            addressService.deleteAddress(uuid);
            return ResponseEntity.ok().build();
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }    
    } 
}