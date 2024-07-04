package com.ramadan.api.dto.costumer.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Schema(name = "LocationGPSUpdateCostumerDto")
public class LocationGPSUpdateCostumerDto {

    @JsonProperty("latitude")
    @Schema(
        name = "latitude",
        type = "number",
        format = "double",
        description = "Latitude coordinate of the position",
        example = "37.7749",
        required = true
    )
    private BigDecimal latitude;

    @JsonProperty("longitude")
    @Schema(
        name = "longitude",
        type = "number",
        format = "double",
        description = "Longitude coordinate of the position",
        example = "-122.4194",
        required = true
    )
    private BigDecimal longitude;
}
