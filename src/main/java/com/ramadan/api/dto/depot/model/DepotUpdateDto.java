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
	@Schema(description = "uuid of the depot updated", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6")
	String uuid;
   

    
	@JsonProperty("description")
    @Schema(description = "Description of the deposit", example = "A leading transportation company")
    private String description;
   
	 @JsonProperty("companyUuid")
	 @Schema(description = "Company UUID associated with the deposit", example = "12345678-90ab-cdef-1234-567890abcdef")
    private String companyUuid;

	  @JsonProperty(" volume")
	  @Schema(description = "Volume of the deposit", example = "100.5")
    private Double volume;

	  @JsonProperty("poids")
	  @Schema(description = "Weight of the deposit", example = "500.0")
    private Double poids;

	  @JsonProperty("codeUnitePoids")
	  @Schema(description = "Unit code of the weight", example = "kg")
    private String codeUnitePoids;

	  @JsonProperty("codeUniteVolume")
	  @Schema(description = "Unit code of the volume", example = "m3")
    private String codeUniteVolume;

	  @JsonProperty("temperature_minimale")
	  @Schema(description = "Minimal temperature of the deposit", example = "10.0")
    private Double temperature_minimale;

	  @JsonProperty("temperature_maximale")
	  @Schema(description = "Maximal temperature of the deposit", example = "25.0")
    private Double temperature_maximale;

	  @JsonProperty(" rang")
	  @Schema(description = "Rang of the deposit", example = "1")
    private Integer rang;

}
