package com.ramadan.api.dto.stock.ligneStock;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
public class LignStockRequestDto {
        @JsonProperty("productUuid")
    @Schema(
        name = "productUuid",
        type = "String",
        description = "Product associated with the lign",
        required = true
    )
    private String productUuid;

    @JsonProperty("stockUuid")
    @Schema(
        name = "stockUuid",
        type = "String",
        description = "stock associated with the unit ling",
        required = true
    )
    private String stockUuid;

    @JsonProperty("name")
    @Schema(
        name = "name",
        type = "string",
        description = "Name of the stock",
        example = "Stock ABC",
        required = true
    )
    private String name;

    @JsonProperty("description")
    @Schema(
        name = "description",
        type = "string",
        description = "Description of the stock",
        example = "Description of Stock ABC",
        required = false
    )
    private String description;
}
