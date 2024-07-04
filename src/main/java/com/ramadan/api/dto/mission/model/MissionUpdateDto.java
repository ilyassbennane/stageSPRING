package com.ramadan.api.dto.mission.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "MissionUpdate")
public class MissionUpdateDto {
    @JsonProperty("uuid")
    @Schema(description = "uuid of the Mission updated", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6")
    String uuid;

   


    @JsonProperty("description")
    @Schema(description = "Description of the mission", example = "A leading transportation company")
    private String description;
}
