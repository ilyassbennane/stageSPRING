package com.ramadan.api.dto.user.email;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.ramadan.api.entity.user.EmailUser}
 */
@Data
@NoArgsConstructor
public class EmailUpdateDto implements Serializable {
    @Schema(description = "UUID of the email", example = "123e4567-e89b-12d3-a456-426614174000")
    @JsonProperty("uuid")
    String uuid;

    @Schema(description = "Code associated with the email update", example = "UPDATE_CODE")
    @JsonProperty("code")
    String code;



    @Schema(description = "New email address", example = "newemail@example.com")
    @JsonProperty("email")
    String email;
}