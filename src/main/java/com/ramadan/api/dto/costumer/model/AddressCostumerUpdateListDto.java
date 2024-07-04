package com.ramadan.api.dto.costumer.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramadan.api.dto.agance.model.AddressAgencyUpdateDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Schema(name = "AddressCostumerUpdateListDto")
public class AddressCostumerUpdateListDto {
    @JsonProperty("uuid")
    @Schema(description = "UUID of the costumer updated", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6", required = true)
    private String uuid;
    
    @JsonProperty("addresses")
    @Schema(description = "List of adress updates", required = true)
    private List<AddressAgencyUpdateDto> addresses;

}
