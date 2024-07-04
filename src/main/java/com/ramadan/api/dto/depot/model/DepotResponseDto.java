package com.ramadan.api.dto.depot.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramadan.api.dto.company.model.CompanyResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.ramadan.api.entity.Depot}
 */
@Data
@NoArgsConstructor
@Schema(name = "Depot")
public class DepotResponseDto {
    @JsonProperty("uuid")
    @Schema(description = "Uuid of the deposit", example = "MISS001kyfuzeavyICUKTE")
    String uuid;
    @JsonProperty("code")
    @Schema(description = "Code of the deposit", example = "MISS001")
    private String code;

    @JsonProperty("description")
    @Schema(description = "Description of the deposit", example = "A leading transportation company")
    private String description;

    @JsonProperty("compan")
    @Schema(description = "Company associated with the deposit", example = "12345678-90ab-cdef-1234-567890abcdef")
    private CompanyResponseDto company;

    @JsonProperty("isMain")
    @Schema(description = "show it is the main deposit of company ", example = "false")
    private boolean isMain;

    @JsonProperty("volume")
    @Schema(description = "Volume of the deposit", example = "100.5")
    private Double volume;

    @JsonProperty("poids")
    @Schema(description = "Weight of the deposit", example = "500.0")
    private Double poids;

    @JsonProperty("codeUnitePoids")
    @Schema(description = "Unit code of the weight", example = "kg")
    private String codeUnitePoids;

    @JsonProperty("codeUniteVolume")
    @Schema(description = "Unit code of the volume", example = "m3")
    private String codeUniteVolume;

    @JsonProperty("temperature_minimale")
    @Schema(description = "Minimal temperature of the deposit", example = "10.0")
    private Double temperature_minimale;

    @JsonProperty("temperature_maximale")
    @Schema(description = "Maximal temperature of the deposit", example = "25.0")
    private Double temperature_maximale;

    @JsonProperty("rang")
    @Schema(description = "Rang of the deposit", example = "1")
    private Integer rang;


}
