package com.ramadan.api.dto.costumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "EmailCostumer")
@Data
@NoArgsConstructor
public class EmailCostumerResponseDto {
	
	@JsonProperty("uuid")
	@Schema(name = "uuid", type = "string", description = "UUID of the email", example = "e823a3b9-24e6-4a15-9a2d-8e7b463e9dbd", required = true)
	String uuid;

	@JsonProperty("costumerUuid")
	@Schema(name = "customerUuid", type = "string", description = "Uuid of the costumer", example = "harness", required = true)
	private String costumerUuid;
	
	@JsonProperty("emailCostumer")
	@Schema(description = "Email of the costumer", example = "ilyass@gmail.com", required = true)
	private String email;
	@JsonProperty("isMain")
	@Schema(name = "isMain", type = "string", description = "Main address or no", example = "true", required = true)
	private boolean isMain;


}
