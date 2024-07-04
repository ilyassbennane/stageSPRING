package com.ramadan.api.dto.device.relationdeviceutilisateur;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "RelationDeviceUtilisateurRequestDto")
public class RelationDeviceUtilisateurRequestDto {

    @JsonProperty("userUuid")
    @Schema(
        name = "userUuid",
        type = "string",
        description = "ID of the user",
        example = "1",
        required = true
    )
    private String userUuid;

    @JsonProperty("deviceUuid")
    @Schema(
        name = "deviceUuid",
        type = "string",
        description = "ID of the device",
        example = "1",
        required = true
    )
    private String deviceUuid;

    @JsonProperty("rang")
    @Schema(
        name = "rang",
        type = "integer",
        description = "Rang",
        example = "1",
        required = false
    )
    private Integer rang;

    @JsonProperty("codeTypeAffection")
    @Schema(
        name = "codeTypeAffection",
        type = "string",
        description = "Code type affection",
        example = "ABC123",
        required = true
    )
    private String codeTypeAffection;

    @JsonProperty("dateAffectation")
    @Schema(
        name = "dateAffectation",
        type = "string",
        description = "Date affectation",
        example = "2024-05-15",
        required = true
    )
    private String dateAffectation;

    @JsonProperty("dateDebut")
    @Schema(
        name = "dateDebut",
        type = "string",
        description = "Date debut",
        example = "2024-05-15",
        required = true
    )
    private String dateDebut;

    @JsonProperty("dateFin")
    @Schema(
        name = "dateFin",
        type = "string",
        description = "Date fin",
        example = "2024-05-20",
        required = false
    )
    private String dateFin;

    // Other fields if any
}
