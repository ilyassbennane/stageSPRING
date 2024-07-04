package com.ramadan.api.dto.familyCostumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "FamilyCostumerUpdate")
public class FamilyCostumerUpdateDto {

	@JsonProperty("uuid")
	@Schema(name = "uuid", type = "string", description = "uuid of the familyCostumer updated", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6")
	private String uuid;

	@JsonProperty
	@Schema(name = "name", type = "string", description = "Name of the familyCostumer", example = "family name")
	private String name;

	@JsonProperty("parent_uuid")
	@Schema(description = "The UUID of the parent familyCostumer", example = "efgh-5678")
	private String parentUuid;

	@JsonProperty("rang")
	@Schema(name = "rang", type = "Integer", description = "The rang of the familyCostumer customer", example = "1")
	private Integer rang;

	@JsonProperty("description")
	@Schema(name = "description", type = "string", description = "The description of the familyCostumer customer", example = "description ParentFamily customer")
	private String description;

}
