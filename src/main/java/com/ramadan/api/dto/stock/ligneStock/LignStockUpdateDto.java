package com.ramadan.api.dto.stock.ligneStock;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class LignStockUpdateDto {
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
