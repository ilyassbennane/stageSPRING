package com.ramadan.api.dto.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResetPassword {

    @Schema(description = "Current password of the user", example = "********")
    private String oldPassword;

    @Schema(description = "New password to be set by the user", example = "********")
    private String newPassword;

    @Schema(description = "Confirmation of the new password", example = "********")
    private String confirmPassword;

}
