package com.ramadan.api.dto.user.PhoneUser;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for retrieving {@link com.ramadan.api.entity.user.PhoneUser}
 */
@Data
@NoArgsConstructor
public class PhoneUserResponseDto implements Serializable {

    @Schema(description = "UUID of the phone user")
    @JsonProperty("uuid")
    private String uuid;

    @Schema(description = "Flag indicating if the phone number is valid")
    @JsonProperty("isValid")
    private boolean isValid;

    @Schema(description = "Flag indicating if the phone number is the main number for the user")
    @JsonProperty("isMain")
    private boolean isMain;

    @Schema(description = "Phone number of the user")
    @JsonProperty("number")
    private String number;
}
