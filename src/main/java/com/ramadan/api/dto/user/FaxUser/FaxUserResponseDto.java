package com.ramadan.api.dto.user.FaxUser;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

/**
 * DTO for {@link com.ramadan.api.entity.user.FaxUser}
 */
@Value
public class FaxUserResponseDto implements Serializable {
    @Schema(description = "UUID of the entity", example = "123e4567-e89b-12d3-a456-556642440000")
    @JsonProperty("uuid")
    private String uuid;

    @Schema(description = "Description of the entity")
    @JsonProperty("description")
    private String description;

    @Schema(description = "Fax number of the entity", example = "+123456789")
    @JsonProperty("fax")
    private String fax;

    @Schema(description = "Code of the entity")
    @JsonProperty("code")
    private String code;


}