package com.ramadan.api.dto.user.Profile.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "ProfileRequestDto")
public class ProfileRequestDto {


    @JsonProperty("name")
    @Schema(description = "Name of the profile", example = "livreur", required = true)
    @NotBlank(message = "name is required.")
    private String name;

    @Schema(description = "Description of the profile")
    @JsonProperty("description")
    @NotBlank(message = "description is required.")
    private String description;
 
    @JsonProperty("companyUuid")
    @Schema(description = "Company UUID", example = "12345678-90ab-cdef-1234-567890abcdef", required = true)
    @NotBlank(message = "Company UUID is required.")
    private String companyUuid;
    
}
