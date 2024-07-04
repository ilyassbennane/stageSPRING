package com.ramadan.api.dto.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleAssignment {

    @Schema(description = "Name of the role", example = "ROLE_ADMIN")
    private String name;

    @Schema(description = "Unique identifier of the role", example = "1234567890")
    private String id;

}
