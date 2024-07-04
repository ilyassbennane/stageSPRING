package com.ramadan.api.dto.secteur.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "SecteurRequest")
public class SecteurRequestDto {

	@JsonProperty("description")
	@Schema(name = "description", type = "string", description = "Description of the sector", example = "secteur 1")
	private String description;

	@JsonProperty("agency_uuid")
	@Schema(name = "agency_uuid", type = "string", description = "agency UUID associated with the sector", example = "e8d5d9380f263bfcd46d51abc115323f6e7afaf18ecc5f8e722d6acd05311796")
	private String agencyUuid;

	@JsonProperty("rang")
	@Schema(name = "rang", type = "integer", description = "range of the sector", example = "1")
	@NotNull(message = "Rang is required.")
	private Integer rang;

	@JsonProperty("name")
	@Schema(name = "name", type = "string", description = "name of the sector", example = "sec Ain chok")
	private String name;

}
