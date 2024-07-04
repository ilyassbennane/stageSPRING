package com.ramadan.api.dto.user.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "User")
public class UserDto {

    @Schema(description = "Unique identifier of the user", example = "uuid", required = true )
    private String uuid;

    @Schema(description = "Username of the user", example = "User_2001", required = true )
    private String username;

    @Schema(description = "Name of the user", example = "John Doe", required = false )
    private String name;

    @Schema(description = "Family name of the user", example = "Doe", required = false )
    private String famillyName;

    @Schema(description = "Email address of the user", example = "user@example.com", required = true )
    private String email;

    @Schema(description = "Indicates whether the user's email address is verified", example = "true", required = true )
    private Boolean emailVerified;

    @Schema(description = "List of groups assigned to the user", example = "CASA", required = true )
    private List<String> groups;

    @Schema(description = "Unique identifier of the user in Keycloak", example = "1234567890", required = false )
    private String idKeycloak;

    @Schema(description = "List of roles assigned to the user", example = "[\"ROLE_ADMIN\", \"ROLE_USER\"]", required = false )
    private List<List<String>> roles;

}
