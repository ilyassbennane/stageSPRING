package com.ramadan.api.dto.address.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@Schema(name = "LocationGPSDto")
public class LocationGPSDto {
	   @JsonProperty("uuid")
	    @Schema(
	        name = "uuid",
	        type = "string",
	        description = "UUID of the position",
	        example = "e823a3b9-24e6-4a15-9a2d-8e7b463e9dbd",
	        required = true
	    )
	    private String uuid;

	    @JsonProperty("latitude")
	    @Schema(
	        name = "latitude",
	        type = "number",
	        format = "double",
	        description = "Latitude coordinate of the position",
	        example = "37.7749",
	        required = true
	    )
	    private double latitude;

	    @JsonProperty("longitude")
	    @Schema(
	        name = "longitude",
	        type = "number",
	        format = "double",
	        description = "Longitude coordinate of the position",
	        example = "-122.4194",
	        required = true
	    )
	    private double longitude;
}
