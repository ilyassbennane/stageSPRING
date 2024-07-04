package com.ramadan.api.dto.user.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserKeycloak {

    @Schema(description = "Unique identifier of the user in Keycloak", example = "uuid", required = true )
    private String sub;

    @Schema(description = "Indicates whether the user's email address is verified", example = "true", required = true )
    private Boolean email_verified;

    @Schema(description = "Name of the user", example = "John Doe", required = false )
    private String name;

    @Schema(description = "Preferred username of the user", example = "johndoe", required = false )
    private String preferred_username;

    @Schema(description = "Given name of the user", example = "John", required = false )
    private String given_name;

    @Schema(description = "Family name of the user", example = "Doe", required = false )
    private String family_name;

    @Schema(description = "Email address of the user", example = "user@example.com", required = true )
    private String email;

    @Schema(description = "List of agencies assigned to the user", example = "[\"AGENCY_1\", \"AGENCY_2\"]", required = false )
    private List<String> agences;

    @Schema(description = "List of roles assigned to the user", example = "[\"ROLE_ADMIN\", \"ROLE_USER\"]", required = false )
    private List<String> roles;
	private boolean active;


}
