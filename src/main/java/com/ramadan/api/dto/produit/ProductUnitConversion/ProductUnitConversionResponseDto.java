package com.ramadan.api.dto.produit.ProductUnitConversion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramadan.api.dto.produit.model.ProduitResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ProductUnitConversionResponseDto")
public class ProductUnitConversionResponseDto {

    @JsonProperty("uuid")
    @Schema(
        name = "uuid",
        type = "string",
        description = "UUID of the product unit conversion",
        example = "fgbvsi-gyghbd-6vug8",
        required = true
    )
    private String uuid;

    @JsonProperty("product")
    @Schema(
        name = "product",
        type = "object",
        description = "Product associated with the unit conversion",
        required = true
    )
    private ProduitResponseDto product;

    @JsonProperty("rapportConversion")
    @Schema(
        name = "rapportConversion",
        type = "double",
        description = "Rapport conversion of the product unit",
        example = "1.5",
        required = true
    )
    private Double rapportConversion;

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
