package com.ramadan.api.dto.costumer.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
@Schema(name = "EmailCostumerUpdateListDto")
@Data
@NoArgsConstructor
public class EmailCostumerUpdateListDto {
    @JsonProperty("uuid")
    @Schema(description = "UUID of the Costumer updated", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6", required = true)
    private String uuid;
    @JsonProperty("emails")
    @Schema(description = "List of email updates", required = true)
    private List<EmailCostumerUpdateDto> emails;
}
