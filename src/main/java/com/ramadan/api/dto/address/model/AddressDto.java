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
public class AddressDto {

	@JsonProperty("uuid")
	@Schema(name = "uuid", type = "string", description = "UUID of the address", example = "e823a3b9-24e6-4a15-9a2d-8e7b463e9dbd", required = true)
	String uuid;

	@JsonProperty("code")
	@Schema(name = "code", type = "string", description = "code of the address", example = "e823a3b9-24e6-4a15-9a2d-8e7b463e9dbd", required = true)
	private String code;

	@JsonProperty("codeVille")
	@Schema(name = "codeVille", type = "string", description = "codeVille of the address", example = "e823a3b9-24e6-4a15-9a2d-8e7b463e9dbd", required = true)
	private String codeVille;

	@JsonProperty("adresse1")
	@Schema(name = "adresse1", type = "string", description = "adresse1 of the address", example = "e823a3b9-24e6-4a15-9a2d-8e7b463e9dbd", required = true)
	private String adresse1;

	@JsonProperty("adresse2")
	@Schema(name = "adresse2", type = "string", description = "adresse2 of the address", example = "e823a3b9-24e6-4a15-9a2d-8e7b463e9dbd", required = true)
	private String adresse2;

	@JsonProperty("adresse3")
	@Schema(name = "adresse3", type = "string", description = "adresse3 of the address", example = "e823a3b9-24e6-4a15-9a2d-8e7b463e9dbd", required = true)
	String adresse3;

	@JsonProperty("city")
	@Schema(name = "city", type = "string", description = "City", example = "New York", required = true)
	private String city;

	@JsonProperty("quartier")
	@Schema(name = "quartier", type = "string", description = "quartier", example = "Nevada", required = true)
	private String quartier;

	@JsonProperty("country")
	@Schema(name = "country", type = "string", description = "Country", example = "United States", required = true)
	private String country;

	@JsonProperty("zipCode")
	@Schema(name = "zipCode", type = "string", description = "ZIP code", example = "10001", required = true)
	private String zipCode;

	@JsonProperty("isMain")
	@Schema(name = "isMain", type = "string", description = "Main address or no", example = "true", required = true)
	private boolean isMain;

//	    @JsonProperty("locationGPS")
//	    @Schema(
//	        name = "locationGPS",
//	        type = "Position",
//	        description = "GPS location of the address",
//	        required = true
//	    )
//	 private LocationGPSDto locationGPSDto;
}