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
public class EmailResponseDto implements Serializable {


    @Schema(description = "Indicates if the email is valid", example = "true")
    @JsonProperty("isValid")
    boolean isValid;

    @Schema(description = "Indicates if the email is the main email", example = "true")
    @JsonProperty("isMain")
    boolean isMain;

    @Schema(description = "Email address", example = "example@example.com")
    @JsonProperty("email")
    String email;
}