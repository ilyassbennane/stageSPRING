package com.ramadan.api.dto.depot.relationDepositAgency;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "RelationDepositAgencyRequestDto")
public class RelationDepositAgencyUpdateDto {

    @JsonProperty("uuid")
    @Schema(
        name = "uuid",
        type = "string",
        description = "UUID of the relation deposit agency",
        example = "fgbvsi-gyghbd-6vug8",
        required = true
    )
    private String uuid;

    @JsonProperty("agencyUuid")
    @Schema(
        name = "agencyUuid",
        type = "String",
        description = "ID of the agency",
        example = "JHUIHIUHIU8Y7847341U7DHGEYEGEYEGEYEVE7E474874",
        required = true
    )
    private String agencyUuid;

    @JsonProperty("depositId")
    @Schema(
        name = "depositUuid",
        type = "String",
        description = "ID of the deposit",
        example = "77HH87H8HA83HE89EGH89EHGE89EHGE879EHGE879EGHE98",
        required = true
    )
    private String depositId;

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
