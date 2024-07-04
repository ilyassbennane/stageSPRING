package com.ramadan.api.dto.brand;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "BrandSearchRequest")
public class BrandSearchRequestDto {

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

