package com.ramadan.api.dto.brand;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "BrandRequestDto")
public class BrandDto {
    @JsonProperty("uuid")
    @Schema(
        name = "uuid",
        type = "String",
        description = "ID of the brand",
        example = "2f548578d7cdb967d70bcf099e29f4d5d56292022d333f40a39593301109eddb"
    )
    private String uuid;

    @JsonProperty("rang")
    @Schema(
        name = "rang",
        type = "integer",
        description = "Rang",
        example = "1"
    )
    private Integer rang;

    @JsonProperty("name")
    @Schema(
        name = "name",
        type = "string",
        description = "Name of the brand",
        example = "Brand X"
    )
    private String name;

    @JsonProperty("description")
    @Schema(
        name = "description",
        type = "string",
        description = "Description of the brand",
        example = "Description of Brand X"
    )
    private String description;
}

