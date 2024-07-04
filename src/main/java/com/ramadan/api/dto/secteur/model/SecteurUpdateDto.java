package com.ramadan.api.dto.secteur.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "SecteurUpdate")
public class SecteurUpdateDto {
	@JsonProperty("uuid")
	@Schema(name = "uuid", type = "string", description = "uuid of the agency updated", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6")
	private String uuid;

	@JsonProperty("description")
	@Schema(name = "description", type = "string", description = "Description of the sector", example = "Description of the sector...")
	private String description;

	@JsonProperty("rang")
	@Schema(name = "rang", type = "integer", description = "range of the sector", example = "1")
	private Integer rang;

	@JsonProperty("name")
	@Schema(name = "name", type = "string", description = "name of the sector", example = "sec Ain chok")
	private String name;
}
