package com.ramadan.api.dto.secteur.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import com.ramadan.api.dto.agance.model.AgenceResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "SecteurResponse")
public class SecteurResponseDto {

    @JsonProperty
    @Schema(
            name = "uuid",
            type = "string",
            description = "UUID of the sector",
            example = "550e8400-e29b-41d4-a716-446655440000"
    )
    private String uuid;

    @JsonProperty
    @Schema(
            name = "code",
            type = "string",
            description = "code of the sector",
            example = "550e8400-e29b-41d4-a716-446655440000"
    )
    private String code;

    @JsonProperty
    @Schema(
            name = "description",
            type = "string",
            description = "description of the sector",
            example = "550e8400-e29b-41d4-a716-446655440000"
    )
    private String description;

    @JsonProperty
    @Schema(
            name = "rang",
            type = "string",
            description = "rang of the sector",
            example = "550e8400-e29b-41d4-a716-446655440000"
    )
    private Integer rang;

	 @JsonProperty("name")
	 @Schema(
			   name = "name",
	           type = "string",
	           description = "name of the sector",
	           example = "sec Ain chok"
	   )
  private String name;

    @JsonProperty
    @Schema(
            name = "agency",
            type = "string",
            description = "agency of the sector",
            example = "550e8400-e29b-41d4-a716-446655440000"
    )
    private AgenceResponseDto agency;



}
