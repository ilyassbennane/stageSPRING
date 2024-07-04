package com.ramadan.api.dto.familyCostumer.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "FamilyCostumer")
public class FamilyCostumerDto {

	@JsonProperty
	@Schema(name = "name", type = "string", description = "Name of the company", example = "child name")
	private String name;

	@JsonProperty("rang")
	@Schema(name = "rang", type = "Integer", description = "The rank of the childFamily customer", example = "1")
	private Integer rang;

	@JsonProperty("parentUuid")
	@Schema(name = "parentUuid", type = "string", description = "The UUID of the FamilyCustomer customer", example = "8f730a1e5486e731327f4e65e628a019a283082107046159181fa0696cc18d81")
	private String parentUuid;

	@JsonProperty("description")
	@Schema(name = "description", type = "string", description = "The description of the FamilyCustomer", example = "Regular childFamily customer")
	private String description;

	@JsonProperty("parent")
	@Schema(name = "parent", type = "FamilyCostumer", description = "The description of the FamilyCustomer", example = "Regular childFamily customer")
	private FamilyCostumerDto parent;

	@JsonProperty("children")
	@Schema(name = "children", type = "FamilyCostumer", description = "The description of the childFamily customer", example = "Regular childFamily customer")
	private Set<FamilyCostumerDto> children = new HashSet<>();

}
