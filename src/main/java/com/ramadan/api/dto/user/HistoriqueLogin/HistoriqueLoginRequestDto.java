package com.ramadan.api.dto.user.HistoriqueLogin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating {@link com.ramadan.api.entity.user.HistoriqueLogin}
 */
@Data
@NoArgsConstructor
public class HistoriqueLoginRequestDto implements Serializable {

    @Schema(description = "Code of the historical login")
    @JsonProperty("code")
    private String code;

    @Schema(description = "Description of the historical login")
    @JsonProperty("description")
    private String description;

    @Schema(description = "UUID of the device")
    @JsonProperty("deviceUuid")
    private String deviceUuid;

    @Schema(description = "UUID of the user")
    @JsonProperty("userUuid")
    private String userUuid;

    @Schema(description = "Date of the historical login")
    @JsonProperty("dateHistorique")
    private LocalDateTime dateHistorique;

    @Schema(description = "Latitude of the GPS location")
    @JsonProperty("gpsLatitude")
    private BigDecimal gpsLatitude;

    @Schema(description = "Longitude of the GPS location")
    @JsonProperty("gpsLongitude")
    private BigDecimal gpsLongitude;
}
