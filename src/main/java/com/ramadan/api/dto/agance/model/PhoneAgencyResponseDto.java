package com.ramadan.api.dto.agance.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramadan.api.dto.costumer.model.PhoneCostumerResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "PhoneAgency")
@Data
@NoArgsConstructor
public class PhoneAgencyResponseDto {
	@JsonProperty("uuid")
	@Schema(name = "uuid", type = "string", description = "UUID of the phone", example = "e823a3b9-24e6-4a15-9a2d-8e7b463e9dbd", required = true)
	String uuid;

//	@JsonProperty("agencyUuid")
//	@Schema(name = "agencyUuid", type = "string", description = "Uuid of the costumer", example = "harness", required = true)
//	private String agencyUuid;
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
