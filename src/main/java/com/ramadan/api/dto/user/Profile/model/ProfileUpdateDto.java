package com.ramadan.api.dto.user.Profile.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "ProfileUpdateDto")
public class ProfileUpdateDto {
	
    @JsonProperty("uuid")
    @Schema(description = "UUID of the profile updated", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6")
    @NotBlank(message = "Uuid is required.")
    private String uuid;

    @JsonProperty("name")
    @Schema(description = "Name of the profile", example = "livreur", required = true)
    @NotBlank(message = "name is required.")
    private String name;

    @Schema(description = "Description of the profile")
    @JsonProperty("description")
    @NotBlank(message = "description is required.")
    private String description;
 

}
