package com.ramadan.api.dto.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PasswordInfo {

    @Schema(description = "Type of the password", example = "PASSWORD")
    private String type;

    @Schema(description = "Value of the password", example = "********")
    private String value;

    @Schema(description = "Indicates whether the password is temporary", example = "false")
    private boolean temporary;

}
