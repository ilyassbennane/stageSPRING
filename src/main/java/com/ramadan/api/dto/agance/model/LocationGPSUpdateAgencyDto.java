package com.ramadan.api.dto.agance.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "LocationGPSUpdateAgencyDto")
public class LocationGPSUpdateAgencyDto {
//	@JsonProperty("uuid")
//	@Schema(description = "UUID of the Address updated", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6")
//	private String uuid;	
	

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
