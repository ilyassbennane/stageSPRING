package com.ramadan.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ramadan.api.dto.IMapClassWithDto;
import com.ramadan.api.dto.user.Profile.model.ProfileRequestDto;
import com.ramadan.api.dto.user.Profile.model.ProfileResponseDto;
import com.ramadan.api.dto.user.Profile.model.ProfileUpdateDto;
import com.ramadan.api.entity.user.profile.Profile;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.services.user.profile.IProfileService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import com.ramadan.api.exceptions.ApiError;

@RestController
@Tag(name = "Profile Management Controller", description = "APIs - Create Profile, Update Profile, Get Profile, Get All Profiles, Delete Profile, Search Profiles")
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
@RequestMapping(path = "${URL-BASE}/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {
	
    @Autowired
    IMapClassWithDto<Profile, ProfileResponseDto> profileresponsemapper;
    @Autowired
    IProfileService profileService ;

    
    @Operation(
    	      summary = "Save Profile",
    	      description = "Create Profile REST API is used to save Profile in a database"
    	   )
    	   @ApiResponses({
    		    @ApiResponse(responseCode = "200", description = "Save the Profile", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResponseDto.class))),
    		    @ApiResponse(responseCode = "400", description = "Validation error", content = @Content(mediaType = "application/json"))
    		})
    	   @PostMapping({"create"})
    public ResponseEntity<ProfileResponseDto> createProfile(@Valid @RequestBody ProfileRequestDto profileRequest)
    		throws APIErrorException {
    	ProfileResponseDto profileResponseDto = profileService.createProfile(profileRequest);
        return  ResponseEntity.ok(profileResponseDto);
    }

    @Operation(
  	      summary = "Update Profile by its uuid",
  	      description = "Update Profile REST API is used to Profile a particular Profile in the database"
  	   )
@ApiOperation(value = "Update an existing Profile", notes = "Update an existing Profile with provided UUID.")
@ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Profile updated successfully", content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResponseDto.class))
      }),
      @ApiResponse(responseCode = "400", description = "Invalid input", content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
      }),

})
@PutMapping
public ResponseEntity<ProfileResponseDto> updateProfile(@Valid @RequestBody ProfileUpdateDto profileUpdate) throws APIErrorException {

  return ResponseEntity.ok(profileService.updateProfile(profileUpdate));
}
    
    
    @Operation(
   	      summary = "Get List of Profiles",
   	      description = "Get All Profiles REST API is used to get all the Profiles from the database"
   	   )
    @ApiOperation(value = "Get all Profiles", notes = "Get a list of all Profiles.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved Profiles", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResponseDto.class))
    }) 
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllProfiles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "uuid,desc") String[] sort) throws APIErrorException  {
            Map<String, Object> response = profileService.findAll(page, size, sort);
            return ResponseEntity.ok(response);
       
    }
    
    
    @Operation(
  	      summary = "Get List of profiles of a Company",
  	      description = "Get List of profiles by the uuid of the Company from the database"
  	   )
  @ApiOperation(value = "Get all profiles by Company ", notes = "Get a list of all profiles by Company .")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved profiles", content = {
                  @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResponseDto.class))
          }),

  })
  @GetMapping("/profiles-compny-list/{uuid}")
  public ResponseEntity<Map<String, Object>> findAllByCompany(
      @PathVariable String uuid,
  @RequestParam(defaultValue = "0") int page,
  @RequestParam(defaultValue = "10") int size,
  @RequestParam(defaultValue = "uuid,desc") String[] sort) throws APIErrorException {
  	Map<String, Object> response = profileService.findProfilesByCompany(uuid,page,size,sort);
  return  ResponseEntity.ok(response);
  }

}
