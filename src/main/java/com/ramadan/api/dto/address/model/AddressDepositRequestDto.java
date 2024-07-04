package com.ramadan.api.dto.address.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.ramadan.api.entity.address.Address}
 */
@Data
@NoArgsConstructor
public class AddressDepositRequestDto extends AddressRequestDto {
    @JsonProperty("depositUuid")
    @Schema(description = "Uuid of the  deposit", example = "harness", required = true)
    private String depositUuid;

}