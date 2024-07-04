package com.ramadan.api.dto.agance.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "PhoneAgencyUpdate")
@Data
@NoArgsConstructor
public class PhoneAgencyUpdateDto {
	
    @JsonProperty("uuid")
    @Schema(description = "UUID of the phone updated", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6")
    private String uuid;
	
	@JsonProperty("codeTypePhone")
	@Schema(description = "Type code of the phone", example = "FIX", required = true)
	private String codeTypePhone;

	@JsonProperty("prefix")
	@Schema(description = "Prefix of the phone number", example = "+212", required = true)
	private String prefix;

	@JsonProperty("number")
	@Schema(description = "Phone number", example = "1234567890", required = true)
	private String numbre;

}
