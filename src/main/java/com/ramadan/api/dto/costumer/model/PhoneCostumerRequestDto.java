package com.ramadan.api.dto.costumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "PhoneCostumerRequest")
@Data
@NoArgsConstructor
public class PhoneCostumerRequestDto {
	@JsonProperty("codeTypePhone")
	@Schema(description = "Type code of the phone", example = "FIX", required = true)
	private String codeTypePhone;

	@JsonProperty("prefix")
	@Schema(description = "Prefix of the phone number", example = "+212", required = true)
	private String prefix;

	@JsonProperty("number")
	@Schema(description = "Phone number", example = "1234567890", required = true)
	private String numbre;
	@JsonProperty("isMain")
	@Schema(name = "isMain", type = "string", description = "Main address or no", example = "true", required = true)
	private boolean isMain;


}
