package com.ramadan.api.dto.mission.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "Mission")
public class MissionResponseDto {

    @JsonProperty("uuid")
    @Schema(description = "Uuid of the mission", example = "MISS001kyfuzeavyICUKTE")
    private String uuid;

    @JsonProperty("code")
    @Schema(description = "Code of the mission", example = "MISS001")
    private String code;

    @JsonProperty("description")
    @Schema(description = "Description of the mission", example = "A leading transportation company")
    private String description;

    @JsonProperty("company_uuid")
    @Schema(description = "Company UUID associated with the mission", example = "12345678-90ab-cdef-1234-567890abcdef")
    private String companyUuid;




}
