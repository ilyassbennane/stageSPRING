package com.ramadan.api.dto.stock.ligneStock;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramadan.api.entity.product.Product;
import com.ramadan.api.entity.stock.Stock;

import io.swagger.v3.oas.annotations.media.Schema;

public class LignStockResponseDto {

        @JsonProperty("product")
    @Schema(
        name = "product",
        type = "String",
        description = "Product associated with the lign",
        required = true
    )
    private Product product;

    @JsonProperty("stock")
    @Schema(
        name = "stock",
        type = "stock",
        description = "stock associated with the unit ling",
        required = true
    )
    private Stock stock;

    @JsonProperty("uuid")
    @Schema(
        name = "uuid",
        type = "string",
        description = "UUID of the lign stock",
        example = "fgbvsi-gyghbd-6vug8",
        required = true
    )
    private String uuid;

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
