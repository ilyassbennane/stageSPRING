package com.ramadan.api.dto.costumer.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
@Schema(name = "EmailCostumerUpdateDto")
@Data
@NoArgsConstructor
public class EmailCostumerUpdateDto {
	@JsonProperty("uuid")
	@Schema(description = "UUID of the Email updated", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6")
	private String uuid;
	@JsonProperty("emailCostumer")
	@Schema(description = "Email of the costumer", example = "ilyass@gmail.com", required = true)
	private String email;
}
