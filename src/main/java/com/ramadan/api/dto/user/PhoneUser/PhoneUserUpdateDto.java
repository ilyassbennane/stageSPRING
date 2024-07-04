package com.ramadan.api.dto.user.PhoneUser;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for updating {@link com.ramadan.api.entity.user.PhoneUser}
 */
@Data
@NoArgsConstructor
public class PhoneUserUpdateDto implements Serializable {

    @Schema(description = "UUID of the phone user")
    @JsonProperty("uuid")
    private String uuid;

    @Schema(description = "Phone number of the user")
    @JsonProperty("number")
    private String number;
}
