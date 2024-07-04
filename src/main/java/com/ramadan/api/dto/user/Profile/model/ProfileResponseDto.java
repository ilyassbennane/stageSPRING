package com.ramadan.api.dto.user.Profile.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "ProfileResponseDto")
public class ProfileResponseDto {

    @JsonProperty("uuid")
    @Schema(description = "UUID of the profile updated", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6")
    private String uuid;
	
    @JsonProperty("name")
    @Schema(description = "Name of the profile", example = "livreur", required = true)
    private String name;

    @Schema(description = "Description of the profile")
    @JsonProperty("description")
    private String description;
   

}
