package com.ramadan.api.dto.depot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.ramadan.api.entity.Depot}
 */
@Data
@NoArgsConstructor
@Schema(name = "DepotUpdat")
public class DepotUpdateDto {
	
    @JsonProperty("uuid")
    @Schema(description = "UUID of the deposit updated", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6")
    private String uuid;

	@JsonProperty("isMain")
	@Schema(name = "isMain", type = "boolean", description = "Indicates if the depot is the main depot", example = "true", required = true)
	private boolean isMain;

	@JsonProperty("matricule")
	@Schema(name = "matricule", type = "string", description = "Matricule of the depot", example = "DPT001", required = true)
	private String matricule;

	@JsonProperty("volume")
	@Schema(name = "volume", type = "number", description = "Volume of the depot", example = "1000.5", required = true)
	private Double volume;

	@JsonProperty("poids")
	@Schema(name = "poids", type = "number", description = "Weight of the depot", example = "2000.5", required = true)
	private Double poids;

	@JsonProperty("codeTypeDeposit")
	@Schema(name = "codeTypeDeposit", type = "string", description = "Type code of the deposit", example = "TYPE001", required = true)
	private String codeTypeDeposit;

	@JsonProperty("codeUnitePoids")
	@Schema(name = "codeUnitePoids", type = "string", description = "Unit code for weight", example = "KG", required = true)
	private String codeUnitePoids;

	@JsonProperty("codeUniteVolume")
	@Schema(name = "codeUniteVolume", type = "string", description = "Unit code for volume", example = "L", required = true)
	private String codeUniteVolume;

	@JsonProperty("temperature_minimale")
	@Schema(name = "temperature_minimale", type = "number", description = "Minimum temperature", example = "-20.0", required = true)
	private Double temperature_minimale;

	@JsonProperty("temperature_maximale")
	@Schema(name = "temperature_maximale", type = "number", description = "Maximum temperature", example = "50.0", required = true)
	private Double temperature_maximale;

	@JsonProperty("rang")
	@Schema(name = "rang", type = "integer", description = "Rank of the depot", example = "1", required = true)
	private Integer rang;

}
