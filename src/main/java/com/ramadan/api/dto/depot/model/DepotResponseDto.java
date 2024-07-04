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



}
