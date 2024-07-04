package com.ramadan.api.dto.address.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.ramadan.api.entity.address.Address}
 */
@Data
@NoArgsConstructor
public class AddressRequestDto {
 
	 @JsonProperty("codeVille")
	    @Schema(
	        name = "codeVille",
	        type = "string",
	        description = "codeVille of the address",
	        example = "e823a3b9-24e6-4a15-9a2d-8e7b463e9dbd",
	        required = true
	    )
	 private String codeVille;
	
	 @JsonProperty("address1")
	    @Schema(
	        name = "address1",
	        type = "string",
	        description = "Address1 line 1",
	        example = "123 Main Street",
	        required = true
	    )
	    private String address1;
	 

	    @JsonProperty("address2")
	    @Schema(
	        name = "address2",
	        type = "string",
	        description = "Address line 2",
	        example = "Suite 456",
	        required = false
	    )
	    private String address2;
	    
	    @JsonProperty("quartier")
	    @Schema(
	        name = "quartier",
	        type = "string",
	        description = "quartier",
	        example = "Nevada",
	        required = true
	    )
	    private String quartier;

	    @JsonProperty("address3")
	    @Schema(
	        name = "address3",
	        type = "string",
	        description = "Address line 3",
	        example = "Building A",
	        required = false
	    )
	    private String address3;

	    @JsonProperty("city")
	    @Schema(
	        name = "city",
	        type = "string",
	        description = "City",
	        example = "New York",
	        required = true
	    )
	    private String city;

	    @JsonProperty("country")
	    @Schema(
	        name = "country",
	        type = "string",
	        description = "Country",
	        example = "United States",
	        required = true
	    )
	    private String country;

	    @JsonProperty("zipCode")
	    @Schema(
	        name = "zipCode",
	        type = "string",
	        description = "ZIP code",
	        example = "10001",
	        required = true
	    )
	    private String zipCode;

	    @JsonProperty("positionGPS")
	    @Schema(
	        name = "positionGPS",
	        type = "Position",
	        description = "GPS position of the address",
	        required = true
	    )
	    private LocationGPSRequestDto locationGPSRequestDto;
		@JsonProperty("isMain")
		@Schema(name = "isMain", type = "string", description = "Main address or no", example = "true", required = true)
		private boolean isMain;
    
}