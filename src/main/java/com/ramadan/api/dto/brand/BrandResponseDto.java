package com.ramadan.api.dto.brand;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Schema(name = "BrandResponseDto")
@NoArgsConstructor
public class BrandResponseDto {
    @JsonProperty("uuid")
    @Schema(
        name = "uuid",
        type = "String",
        description = "ID of the brand",
        example = "1bcyjdjkHFIUHDHUI3UE93E839283RY88293H29"
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
