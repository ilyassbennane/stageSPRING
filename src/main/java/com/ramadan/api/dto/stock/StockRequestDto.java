package com.ramadan.api.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "StockRequestDto")
public class StockRequestDto {

    @JsonProperty("companyUuid")
    @Schema(
        name = "companyUuid",
        type = "long",
        description = "ID of the company",
        example = "1",
        required = true
    )
    private String companyUuid;

    @JsonProperty("depositUuid")
    @Schema(
        name = "depositUuid",
        type = "long",
        description = "ID of the deposit",
        example = "1",
        required = true
    )
    private String depositUuid;

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
