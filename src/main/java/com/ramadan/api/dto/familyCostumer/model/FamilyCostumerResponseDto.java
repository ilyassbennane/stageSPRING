package com.ramadan.api.dto.familyCostumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "FamilyCostumerResponse")
public class FamilyCostumerResponseDto {

	@JsonProperty("uuid")
	@Schema(name = "uuid", type = "string", description = "The UUID of the familyCostumer", example = "c8940ee7ce7191a9d129642baa575473864eb7aa39c9c7e8b99b53c6bda9a198")
	private String uuid;

	@JsonProperty
	@Schema(name = "name", type = "string", description = "Name of the familyCostumer", example = "child name")
	private String name;

	@JsonProperty("rang")
	@Schema(name = "rang", type = "Integer", description = "The rank of the familyCostumer", example = "1")
	private Integer rang;


	@JsonProperty("description")
	@Schema(name = "description", type = "string", description = "The description of the familyCostumer customer", example = "Regular childFamily customer")
	private String description;

}
