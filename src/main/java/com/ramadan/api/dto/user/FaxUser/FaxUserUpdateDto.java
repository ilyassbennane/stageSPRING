package com.ramadan.api.dto.user.FaxUser;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.ramadan.api.entity.user.FaxUser}
 */
@Data
@NoArgsConstructor
public class FaxUserUpdateDto implements Serializable {

    @Schema(description = "UUID of the entity", example = "123e4567-e89b-12d3-a456-556642440000")
    @JsonProperty("uuid")
    private String uuid;
    
    @Schema(description = "Description of the user")
    @JsonProperty("description")
    private String description;

    @Schema(description = "Fax number of the user", example = "+123456789")
    @JsonProperty("fax")
    private String fax;
}