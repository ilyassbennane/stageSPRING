package com.ramadan.api.dto.agance.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(name = "PhoneAgencyUpdateList")
@Data
@NoArgsConstructor
public class PhoneAgencyUpdateListDto {
    
    @JsonProperty("uuid")
    @Schema(description = "UUID of the agency updated", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6", required = true)
    private String uuid;
    
    @JsonProperty("phones")
    @Schema(description = "List of phone updates", required = true)
    private List<PhoneAgencyUpdateDto> phones;
}
