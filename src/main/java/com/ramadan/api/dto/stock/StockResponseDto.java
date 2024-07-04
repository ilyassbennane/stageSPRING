package com.ramadan.api.dto.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramadan.api.dto.company.model.CompanyResponseDto;
import com.ramadan.api.dto.depot.model.DepotResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "StockResponseDto")
public class StockResponseDto {

    @JsonProperty("uuid")
    @Schema(
        name = "uuid",
        type = "string",
        description = "UUID of the stock",
        example = "fgbvsi-gyghbd-6vug8",
        required = true
    )
    private String uuid;

    @JsonProperty("company")
    @Schema(
        name = "company",
        type = "object",
        description = "Company associated with the stock",
        required = true
    )
    private CompanyResponseDto company;

    @JsonProperty("deposit")
    @Schema(
        name = "deposit",
        type = "object",
        description = "Deposit associated with the stock",
        required = true
    )
    private DepotResponseDto deposit;

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

    // Other fields if any
}
