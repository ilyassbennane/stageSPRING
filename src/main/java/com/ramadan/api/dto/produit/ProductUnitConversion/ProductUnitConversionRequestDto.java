package com.ramadan.api.dto.produit.ProductUnitConversion;

import com.fasterxml.jackson.annotation.JsonProperty;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductUnitConversionRequestDto {
     @JsonProperty("rapportConversion")
    @Schema(
        name = "rapportConversion",
        type = "double",
        description = "Rapport conversion of the product unit",
        example = "1.5",
        required = true
    )
    private Double rapportConversion;

        @JsonProperty("productUuid")
    @Schema(
        name = "productUuid",
        type = "String",
        description = "Product associated with the unit conversion",
        required = true
    )
    private String productUuid;

    @JsonProperty("codeUnit")
    @Schema(
        name = "codeUnit",
        type = "string",
        description = "Code unit of the product",
        example = "kg",
        required = true
    )
    private String codeUnit;

    @JsonProperty("name")
    @Schema(
        name = "name",
        type = "string",
        description = "Name of the brand",
        example = "Brand X",
        required = true
    )
    private String name;

    @JsonProperty("description")
    @Schema(
        name = "description",
        type = "string",
        description = "Description of the brand",
        example = "Description of Brand X",
        required = false
    )
    private String description;
    
}
