package com.ramadan.api.dto.depot.relationDepositAgency;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramadan.api.dto.agance.model.AgenceResponseDto;
import com.ramadan.api.dto.depot.model.DepotResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "RelationDepositAgencyResponseDto")
public class RelationDepositAgencyResponseDto {

    @JsonProperty("uuid")
    @Schema(
        name = "uuid",
        type = "string",
        description = "UUID of the relation deposit agency",
        example = "fgbvsi-gyghbd-6vug8",
        required = true
    )
    private String uuid;

    @JsonProperty("agency")
    @Schema(
        name = "agency",
        type = "object",
        description = "Agency associated with the relation",
        required = true
    )
    private AgenceResponseDto agency;

    @JsonProperty("deposit")
    @Schema(
        name = "deposit",
        type = "object",
        description = "Deposit associated with the relation",
        required = true
    )
    private DepotResponseDto deposit;

    @JsonProperty("isMain")
    @Schema(
        name = "isMain",
        type = "boolean",
        description = "Flag indicating if this is the main relation",
        example = "true",
        required = true
    )
    private boolean isMain;

    // Other fields if any
}
