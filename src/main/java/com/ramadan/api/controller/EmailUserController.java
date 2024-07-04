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

import com.ramadan.api.dto.user.email.EmailRequestDto;
import com.ramadan.api.dto.user.email.EmailResponseDto;
import com.ramadan.api.dto.user.email.EmailUpdateDto;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.services.user.emailUser.IEmailUserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/email-users")
@Tag(name = "Email Users", description = "Operations pertaining to email users")
public class EmailUserController {

    @Autowired
    private IEmailUserService emailUserService;

    /**
 * Get all email users.
 * 
 * @param page The page number for pagination (default is 0).
 * @param size The page size for pagination (default is 10).
 * @return ResponseEntity containing a map of email users and additional information.
 */
    @ApiOperation(value = "Get all email users", notes = "Get a list of all email users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved email users", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmailResponseDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllEmailUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Map<String, Object> response = emailUserService.findAll(page, size);
            return ResponseEntity.ok(response);
        } catch (APIErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
/**
 * Get an email user by UUID.
 * 
 * @param uuid The UUID of the email user to retrieve.
 * @return ResponseEntity containing the details of the email user if found (status code 200),
 *         or an error response with status code 404 if the email user is not found.
 * @throws APIErrorException if an error occurs during the operation.
 */
    @ApiOperation(value = "Get an email user by UUID", notes = "Get email user details by providing its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved email user", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmailResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Email user not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = APIErrorException.class))
            })
    })
    @GetMapping("/user-email-list/{uuid}")
    public ResponseEntity<EmailResponseDto> getEmailUserByUuid(@PathVariable String uuid) throws APIErrorException {
        return ResponseEntity.ok(emailUserService.findByUuid(uuid));
    }

    /**
 * Create a new email user with the provided details.
 * 
 * @param emailRequest The request body containing the details of the email user to be created.
 * @return ResponseEntity containing the details of the newly created email user with status code 201 if successful,
 *         or an error response with status code 400 if the input is invalid.
 * @throws APIErrorException if an error occurs during the operation.
 */
@PostMapping
@ApiOperation(value = "Create a new email user", notes = "Create a new email user with provided details.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Email user created successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = EmailResponseDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = APIErrorException.class))
        })
})
public ResponseEntity<EmailResponseDto> createEmailUser(@Valid @RequestBody EmailRequestDto emailRequest) throws APIErrorException {
    return new ResponseEntity<>(emailUserService.saveEmailUser(emailRequest), HttpStatus.CREATED);
}

    /**
 * Update an existing email user with the provided details.
 * 
 * @param uuid The UUID of the email user to be updated.
 * @param emailUpdate The request body containing the updated details of the email user.
 * @return ResponseEntity containing the details of the updated email user with status code 200 if successful,
 *         or an error response with status code 400 if the input is invalid.
 * @throws APIErrorException if an error occurs during the operation.
 */
@ApiOperation(value = "Update an existing email user", notes = "Update an existing email user with provided UUID.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Email user updated successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = EmailResponseDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = APIErrorException.class))
        }),
})
@PutMapping
public ResponseEntity<EmailResponseDto> updateEmailUser( @Valid @RequestBody EmailUpdateDto emailUpdate) throws APIErrorException {
    return ResponseEntity.ok(emailUserService.updateEmailUser(emailUpdate.getUuid(), emailUpdate));
}

    /**
 * Delete an email user by UUID.
 * 
 * @param uuid The UUID of the email user to be deleted.
 * @return ResponseEntity with no content and status code 204 if the email user is deleted successfully,
 *         or an error response with status code 404 if the email user is not found.
 * @throws APIErrorException if an error occurs during the operation.
 */
@ApiOperation(value = "Delete an email user by UUID", notes = "Delete an email user by providing its UUID.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Email user deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Email user not found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = APIErrorException.class))
        })
})
@DeleteMapping("/{uuid}")
public ResponseEntity<Void> deleteEmailUser(@PathVariable String uuid) throws APIErrorException {
    emailUserService.deleteEmailUser(uuid);
    return ResponseEntity.noContent().build();
}
}
