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
public class EmailRequestDto implements Serializable {

    @Schema(description = "Email address", example = "example@example.com")
    @JsonProperty("email")
    String email;

    @Schema(description = "User UUID", example = "123e4567-e89b-12d3-a456-426614174000")
    @JsonProperty("userUuid")
    String userUuid;


}