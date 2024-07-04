package com.ramadan.api.dto.mission.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "MissionRequest")
public class MissionRequestDto {




   @Schema(description = "name of the mission", example = "livrisan")
   private String name;


    @JsonProperty("description")
    @Schema(description = "Description of the mission", example = "A leading transportation company")
    private String description;

    @JsonProperty("company_uuid")
    @Schema(description = "Company UUID associated with the mission", example = "12345678-90ab-cdef-1234-567890abcdef")
    private String companyUuid;
}
