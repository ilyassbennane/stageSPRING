package com.ramadan.api.dto.user.HistoriqueLogin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

/**
 * DTO for updating {@link com.ramadan.api.entity.user.HistoriqueLogin}
 */
@Value
public class HistoriqueLoginUpdateDto implements Serializable {


    @Schema(description = "Description of the historical login")
    @JsonProperty("description")
    String description;

    @Schema(description = "UUID of the device")
    @JsonProperty("deviceUuid")
    String deviceUuid;

    @Schema(description = "UUID of the user")
    @JsonProperty("userUuid")
    String userUuid;

    @Schema(description = "Date of the historical login")
    @JsonProperty("dateHistorique")
    LocalDateTime dateHistorique;

    @Schema(description = "Latitude of the GPS location")
    @JsonProperty("gpsLatitude")
    BigDecimal gpsLatitude;

    @Schema(description = "Longitude of the GPS location")
    @JsonProperty("gpsLongitude")
    BigDecimal gpsLongitude;
}
