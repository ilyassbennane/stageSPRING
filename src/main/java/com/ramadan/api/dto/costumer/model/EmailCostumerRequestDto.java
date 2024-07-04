package com.ramadan.api.dto.costumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
@Schema(name = "EmailCostumerRequest")
@Data
@NoArgsConstructor
public class EmailCostumerRequestDto {

	@JsonProperty("emailCostumer")
	@Schema(description = "Email of the costumer", example = "ilyass@gmail.com", required = true)
	private String email;
	@JsonProperty("isMain")
	@Schema(name = "isMain", type = "string", description = "Main address or no", example = "true", required = true)
	private boolean isMain;

}
