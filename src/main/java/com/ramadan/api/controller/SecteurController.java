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

import com.ramadan.api.dto.address.model.LocationGPSDto;
import com.ramadan.api.dto.address.model.LocationGPSRequestDto;
import com.ramadan.api.dto.address.model.LocationGPSSectorRequestDto;
import com.ramadan.api.dto.costumer.model.CostumerDto;
import com.ramadan.api.dto.costumer.model.CostumerFilterRequestDto;
import com.ramadan.api.dto.secteur.model.SecteurRequestDto;
import com.ramadan.api.dto.secteur.model.SecteurResponseDto;
import com.ramadan.api.dto.secteur.model.SecteurUpdateDto;
import com.ramadan.api.dto.secteur.model.SectorSearchFilterDto;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiError;
import com.ramadan.api.exceptions.ApiKeyException;
import com.ramadan.api.services.secteur.ISecteurService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Authentication is required to access the resource.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        }),
        @ApiResponse(responseCode = "404", description = "Secteurs not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
})
@RestController
@RequestMapping("${URL-BASE}/sector")
@Tag(name= "Secteur Management Controller", description = "APIs - Create Sector, Update Sector, Get Sector, Get All Sectors, Delete Sector, GetSectorsByUuidAgency ")
public class SecteurController {
    @Autowired
    ISecteurService secteurService ;


    @Operation(summary = "Save Sector", description = "Create Sector REST API is used to save Sector in a database")
@PostMapping
@ApiOperation(value = "Create a new Sector", notes = "Create a new Sector with provided details.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Sector created successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = SecteurResponseDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        })
})
public ResponseEntity<SecteurResponseDto> createSecteur(@Valid @RequestBody SecteurRequestDto secteurRequestDto) throws APIErrorException {
    // Placeholder
    return new ResponseEntity<>(secteurService.saveSecteur(secteurRequestDto), HttpStatus.CREATED);
}

    @Operation(
    	      summary = "Update Sector by its uuid",
    	      description = "Update Sector REST API is used to update a particular Sector in the database"
    	   )
@ApiOperation(value = "Update an existing Sector", notes = "Update an existing Sector with provided UUID.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Secteur updated successfully", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = SecteurResponseDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
        }),

})
@PutMapping
public ResponseEntity<SecteurResponseDto> updateSecteur( @Valid @RequestBody SecteurUpdateDto secteurUpdate) throws ApiKeyException {

    return ResponseEntity.ok(secteurService.updateEntity(secteurUpdate.getUuid(),secteurUpdate));
}

@ApiOperation(value = "Get a Sector by UUID", notes = "Get Sector details by providing its UUID.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved secteur", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = SecteurResponseDto.class))
        }),

})

@Operation(
	      summary = "Get Sector by its uuid",
	      description = "Get Sector By ID REST API is used to get a single Sector from the database"
	   )
@GetMapping("/{uuid}")
public ResponseEntity<SecteurResponseDto> getSecteurByUuid(@PathVariable String uuid) throws APIErrorException {
    return ResponseEntity.ok(secteurService.findByUuid(uuid));
}

@Operation(
	      summary = "Delete Sector by its uuid",
	      description = "Delete Sector REST API is used to delete a particular Sector from the database"
	   )
@ApiOperation(value = "Delete a Sector by UUID", notes = "Delete a Sector by providing its UUID.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Sector deleted successfully"),
})
@DeleteMapping("/{uuid}")
public ResponseEntity<Void> deleteSecteur(@PathVariable String uuid) throws APIErrorException {
    secteurService.deleteSecteur(uuid);
    return ResponseEntity.noContent().build();
}

/**********************************************
 * Get list Sector
 * @throws APIErrorException
 ******************************************/
@Operation(summary = "Get List Sectors", description = "Get All Sectors REST API is used to get all the Sectors from the database")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the Sectors", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = CostumerDto.class)) }),
        @ApiResponse(responseCode = "404", description = "Sectors not found", content = @Content) })
@PostMapping("search")
public ResponseEntity<Map<String, Object>> search(@Valid @RequestBody(required = false) SectorSearchFilterDto searchRequestDto,
                                                  @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size,
                                                  @RequestParam(defaultValue = "id,desc") String[] sort)
        throws APIErrorException {

    Map<String, Object> lSectorsMap = secteurService.search(searchRequestDto, page, size, sort);

    return new ResponseEntity<>(lSectorsMap, HttpStatus.OK);
}
@Operation(
	      summary = "Get List of Sectos of an agency",
	      description = "Get List of sectors by the uuid of the Agency from the database"
	   )
@ApiOperation(value = "Get all Sectors by agency ", notes = "Get a list of all Sectors by agency .")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved secteurs", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = SecteurResponseDto.class))
        }),

})
@GetMapping("/sectors-agency-list/{uuid}")
public ResponseEntity<Map<String, Object>> findAllByAgency(
    @PathVariable String uuid,
@RequestParam(defaultValue = "0") int page,
@RequestParam(defaultValue = "10") int size,
@RequestParam(defaultValue = "uuid,desc") String[] sort) throws APIErrorException {
	Map<String, Object> response = secteurService.findAllByAgency(uuid,page,size,sort);
return  ResponseEntity.ok(response);
}

@Operation(
	      summary = "Add locations to a sector",
	      description = "Add Multiple Locations to a sector "
	   )
@PostMapping("/sectors/{uuid}/locations")
public ResponseEntity<Void> addLocationGpsToSector(@PathVariable String uuid, @RequestBody List<LocationGPSSectorRequestDto> locations) throws APIErrorException{
        return ResponseEntity.ok().build();

}

}
