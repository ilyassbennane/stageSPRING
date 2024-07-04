package com.ramadan.api.dto.user.model;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class KeycloakUser {

    @Schema(description = "Timestamp when the user was created", example = "16409952000")
    private Long createdTimestamp;

    @Schema(description = "Username of the user", example = "john_doe")
    private String username;

    @Schema(description = "Indicates whether the user's account is enabled", example = "true")
    private boolean enabled;

    @Schema(description = "Indicates whether the user has two-factor authentication enabled", example = "true")
    private boolean totp;

    @Schema(description = "Indicates whether the user's email is verified", example = "true")
    private boolean emailVerified;

    @Schema(description = "First name of the user", example = "John")
    private String firstName;

    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;

    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;

    @Schema(description = "List of credential types that can be disabled for the user", example = "[\"PASSWORD\", \"EMAIL\"]")
    private List<String> disableableCredentialTypes;

    @Schema(description = "List of actions that the user must perform before accessing certain resources", example = "[\"VERIFY_EMAIL\", \"CONFIRM_ACCOUNT\"]")
    private List<String> requiredActions;

    @Schema(description = "Timestamp indicating the time before which the user's access token is valid", example = "16409952000")
    private int notBefore;

    @Schema(description = "Map of access rights granted to the user", example = "{ \"ROLE_ADMIN\": true, \"ROLE_USER\": false }")
    private Map<String, Boolean> access;

    @Schema(description = "List of roles assigned to the user in the realm", example = "[\"ROLE_ADMIN\", \"ROLE_USER\"]")
    private List<String> realmRoles;

    @Schema(description = "List of credentials associated with the user", example = "[\"PASSWORD\", \"EMAIL\"]")
    private List<Object> credentials;

}
