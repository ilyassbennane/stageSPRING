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
public class FaxUserRequestDto implements Serializable {
    @Schema(description = "Description of the fax request", example = "Request to update fax number")
    @JsonProperty("description")
    String description;

    @Schema(description = "Fax number", example = "123-456-7890")
    @JsonProperty("fax")
    String fax;
}