package com.ramadan.api.dto.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefreshTocken {
    @JsonProperty("refresh_token")
    @Schema(name="refresh_token" , description="The refresh token ") 
    private String refresh_token;
}
