package com.ramadan.api.dto.familyCostumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "FamilyCostumerRequest")
public class FamilyCostumerRequestDto {

	@JsonProperty
	@Schema(name = "name", type = "string", description = "Name of the familyCostumer", example = "familyCostumer name")
	@NotBlank(message = "name is mandatory")
	private String name;

	@JsonProperty("rang")
	@Schema(name = "rang", type = "Integer", description = "The rang of the familyCostumer ", example = "1")
	private Integer rang;

	@JsonProperty("parentUuid")
	@Schema(name = "parentUuid", type = "string", description = "The UUID of the parent familyCostumer", example = "8f730a1e5486e731327f4e65e628a019a283082107046159181fa0696cc18d81")
	private String parentUuid;

	@JsonProperty("description")
	@Schema(name = "description", type = "string", description = "The description of the familyCostumer", example = "Regular childFamily customer")
	@NotBlank(message = "Description is mandatory")
	private String description;

}
