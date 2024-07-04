package com.ramadan.api.dto.user.Profile.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for retrieving {@link com.ramadan.api.entity.user.profile.Profile}
 */
@Data
@NoArgsConstructor
@Schema(name = "ProfileDto")
public class ProfileDto{

    @Schema(description = "ID of the profile")
    @JsonProperty("id")
    private Long id;

    @Schema(description = "UUID of the profile")
    @JsonProperty("uuid")
    private String uuid;

    @Schema(description = "Code of the profile")
    @JsonProperty("code")
    private String code;

    @Schema(description = "Description of the profile")
    @JsonProperty("description")
    private String description;


    @Schema(description = "Flag indicating if the profile is valid")
    @JsonProperty("isValid")
    private boolean isValid;

    @Schema(description = "Date and time when the profile was created")
    @JsonProperty("createDateTime")
    private LocalDateTime createDateTime;

    @Schema(description = "Date and time when the profile was last updated")
    @JsonProperty("updateDateTime")
    private LocalDateTime updateDateTime;

    @Schema(description = "UUID of the company associated with the profile")
    @JsonProperty("companyUuid")
    private String companyUuid;
}
