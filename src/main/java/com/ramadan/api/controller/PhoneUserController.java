package com.ramadan.api.controller;

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

import com.ramadan.api.dto.user.PhoneUser.PhoneUserRequestDto;
import com.ramadan.api.dto.user.PhoneUser.PhoneUserResponseDto;
import com.ramadan.api.dto.user.PhoneUser.PhoneUserUpdateDto;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.services.user.phone.PhoneUserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${URL-BASE}/phone-user")
@Tag(name = "Phone Users", description = "Operations related to phone users")
public class PhoneUserController {

    @Autowired
    private PhoneUserService phoneUserService;


/**
 * Retrieves the details of a phone user by UUID.
 * 
 * @param uuid The unique identifier of the phone user
 * @return ResponseEntity containing the details of the phone user if found, or an error response if not found
 */
    @ApiOperation(value = "Get phone user by UUID", notes = "Get phone user details by UUID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved phone user details"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/{uuid}")
    public ResponseEntity<PhoneUserResponseDto> getPhoneUserByUuid(@PathVariable String uuid) {
        try {
            PhoneUserResponseDto phoneUser = phoneUserService.findByUuid(uuid);
            return ResponseEntity.ok(phoneUser);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
/**
 * Retrieves a list of all phone users with pagination support.
 * 
 * @param page The page number for pagination (default is 0)
 * @param size The page size for pagination (default is 10)
 * @return ResponseEntity containing a map of phone user details if found, or an error response if not found
 */
    @ApiOperation(value = "Get all phone users", notes = "Get a list of all phone users.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of phone users"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllPhoneUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> response = phoneUserService.findAll(page, size);
            return ResponseEntity.ok(response);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
 * Create a new phone user.
 * 
 * @param requestDto The request data for creating the new phone user
 * @return ResponseEntity containing the details of the created phone user if successful, or an error response if not successful
 */
@ApiOperation(value = "Create a new phone user", notes = "Create a new phone user.")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully created new phone user"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@PostMapping
public ResponseEntity<PhoneUserResponseDto> createPhoneUser(@RequestBody PhoneUserRequestDto requestDto) {
    try {
        PhoneUserResponseDto createdPhoneUser = phoneUserService.savePhoneUser(requestDto);
        return ResponseEntity.ok(createdPhoneUser);
    } catch (APIErrorException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
    /**
 * Update an existing phone user.
 * 
 * @param requestDto The request data for updating the existing phone user
 * @param uuid The unique identifier of the phone user to be updated
 * @return ResponseEntity containing the details of the updated phone user if successful, or an error response if not successful
 */
@ApiOperation(value = "Update an existing phone user", notes = "Update an existing phone user.")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully updated phone user"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@PutMapping
public ResponseEntity<PhoneUserResponseDto> updatePhoneUser(@RequestBody PhoneUserUpdateDto requestDto) {   try {
            PhoneUserResponseDto updatedPhoneUser = phoneUserService.updatePhoneUser(requestDto.getUuid(),requestDto);
            return ResponseEntity.ok(updatedPhoneUser);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
 * Delete a phone user by UUID.
 * 
 * @param uuid The unique identifier of the phone user to be deleted
 * @return ResponseEntity containing no content if the phone user is successfully deleted, or an error response if not successful
 */
@ApiOperation(value = "Delete a phone user by UUID", notes = "Delete a phone user by UUID.")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully deleted phone user"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@DeleteMapping("/{uuid}")
public ResponseEntity<Void> deletePhoneUser(@PathVariable String uuid) {
    try {
        phoneUserService.deletePhoneUser(uuid);
        return ResponseEntity.ok().build();
    } catch (APIErrorException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
}

