package com.ramadan.api.dto.familyCostumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "FamilyCostumerSearchRequest")
public class FamilyCostumerSearchRequestDto {


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
