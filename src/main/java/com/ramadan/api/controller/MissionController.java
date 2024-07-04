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

import com.ramadan.api.dto.mission.model.MissionRequestDto;
import com.ramadan.api.dto.mission.model.MissionResponseDto;
import com.ramadan.api.dto.mission.model.MissionUpdateDto;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiError;
import com.ramadan.api.services.mission.IMissionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${URL-BASE}/mission")
@Tag(name = "Missions", description = "Operations pertaining to missions")
public class MissionController {
   @Autowired
    IMissionService missionService ;
    @ApiOperation(value = "Get all missions", notes = "Get a list of all missions.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved missions", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MissionResponseDto.class))
            }),
    })
    @GetMapping("all")
    public ResponseEntity<Map<String, Object>> getAllMissions(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "uuid,desc") String[] sort
    ) throws APIErrorException {
        // Placeholder
        return ResponseEntity.ok(missionService.findAll(page,size,sort));
    }

    @ApiOperation(value = "Get a mission by compani", notes = "Get mission details by providing its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved mission", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MissionResponseDto.class))
            }),

    })

    @GetMapping("/missions-company-list/{uuid}")
    public ResponseEntity<Map<String, Object>> getAllMissionsByCompany(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable String uuid ,
            @RequestParam(defaultValue = "uuid,desc") String[] sort
    ) throws APIErrorException {
        // Placeholder
        return ResponseEntity.ok(missionService.findAllByCompany(uuid,page,size,sort));
    }

    @ApiOperation(value = "Get a mission by UUID", notes = "Get mission details by providing its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved mission", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MissionResponseDto.class))
            }),

    })

    @GetMapping("/{uuid}")
    public ResponseEntity<MissionResponseDto> getMissionByUuid(@PathVariable String uuid) throws APIErrorException {
        // Placeholder
        return ResponseEntity.ok(missionService.findByUuid(uuid));
    }
    @ApiOperation(value = "assigne tour to user ", notes = "assigne tour to user by providing them UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully assigned tour"),

    })


    @PostMapping("/affect-mission-to-user")
 public ResponseEntity<Void> afecterMissionToUser(  @RequestParam String missionUuid, @RequestParam String userUuid) throws APIErrorException {
    missionService.assignMissionToUser(missionUuid, userUuid);
    return ResponseEntity.noContent().build();
}
    @ApiOperation(value = "assigne tour to costumer ", notes = "assigne tour to costumer by providing them UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully assigned tour"),

    })
@PostMapping("/affect-mission-to-cotumer")
public ResponseEntity<Void>affecterMissinToCostumer(@RequestParam String missiUuid,@RequestParam String costumerUuid)throws APIErrorException{
   missionService.assignMissionToUser(missiUuid,costumerUuid);
    return ResponseEntity.noContent().build();

    }
    @ApiOperation(value = "assigne tour to agency ", notes = "assigne tour to agency by providing them UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully assigned tour"),

    })
@PostMapping("/affect-mission-to-agency")
public ResponseEntity<Void> afecterMissionToAgency(  @RequestParam String missionUuid, @RequestParam String agencyUuid) throws APIErrorException {
    missionService.assignMissionToAgency(missionUuid, agencyUuid);
    return ResponseEntity.noContent().build();
}

    @ApiOperation(value = "Create a new mission", notes = "Create a new mission with provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mission created successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MissionResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
            })
    })
    @PostMapping
    public ResponseEntity<MissionResponseDto> createMission(@Valid @RequestBody MissionRequestDto missionRequestDto) throws APIErrorException {
        // Placeholder
        return new ResponseEntity<>(missionService.saveMission(missionRequestDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update an existing mission", notes = "Update an existing mission with provided UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mission updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MissionResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
            }),

    })
    @PutMapping
    public ResponseEntity<MissionResponseDto> updateMission( @Valid @RequestBody MissionUpdateDto missionUpdateDTO) {
        // Placeholder
        return ResponseEntity.ok(missionService.updateEntity(missionUpdateDTO.getUuid(),missionUpdateDTO));
    }

    @ApiOperation(value = "Delete a mission by UUID", notes = "Delete a mission by providing its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Mission deleted successfully"),

    })
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteMission(@PathVariable String uuid) throws APIErrorException {
        missionService.deleteMission(uuid);
        return ResponseEntity.noContent().build();
    }
    @ApiOperation(value = "Get all missions by agence", notes = "Get a list of all missions by agence.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved missions", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MissionResponseDto.class))
            }),
    })
    @GetMapping("/agency")
    public ResponseEntity<List<MissionResponseDto>> getMissionsByAgence(@RequestParam String uuid) {
//      List<MissionResponseDto> missions = missionService.findAllByAgence(uuid);
        return ResponseEntity.ok(null);
    }

    @ApiOperation(value = "Get a mission by user", notes = "Get missions list by user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved mission", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MissionResponseDto.class))
            }),

    })

    @GetMapping("/missions-user-list/{uuid}")
    public ResponseEntity<Map<String, Object>> getAllMissionsByUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable String uuid ,
            @RequestParam(defaultValue = "uuid,desc") String[] sort
    ) throws APIErrorException {
        // Placeholder
        return ResponseEntity.ok(missionService.findAllByUser(uuid,page,size,sort));
    }

    @ApiOperation(value = "Get a mission by costumer", notes = "Get missions list by costumer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved mission", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MissionResponseDto.class))
            }),

    })

    @GetMapping("/missions-costumer-list/{uuid}")
    public ResponseEntity<Map<String, Object>> getAllMissionsByCostumer(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable String uuid ,
            @RequestParam(defaultValue = "uuid,desc") String[] sort
    ) throws APIErrorException {
        // Placeholder
        return ResponseEntity.ok(missionService.findAllByClient(uuid,page,size,sort));
    }

    @ApiOperation(value = "Get a mission by agence", notes = "Get missions list by agency.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved mission", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MissionResponseDto.class))
            }),

    })

    @GetMapping("/missions-agency-list/{uuid}")
    public ResponseEntity<Map<String, Object>> getAllMissionsByAgence(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable String uuid ,
            @RequestParam(defaultValue = "uuid,desc") String[] sort
    ) throws APIErrorException {
        // Placeholder
        return ResponseEntity.ok(missionService.findAllByAgence(uuid,page,size,sort));
    }

}
