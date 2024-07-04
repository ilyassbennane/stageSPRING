package com.ramadan.api.dto.agance.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramadan.api.entity.agence.AdresseAgency;
import com.ramadan.api.entity.agence.PhoneAgency;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.ramadan.api.entity.agence.Agency}
 */
@Data
@NoArgsConstructor
@Schema(name = "Agence")
public class AgenceResponseDto {

    @JsonProperty("uuid")
    @Schema(description = "uuid of the agency", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6")
    private String uuid;

    @JsonProperty("code")
    @Schema(description = "Code of the agency", example = "AG001", required = true)
    private String code;

    @JsonProperty("name")
    @Schema(description = "Name of the agency", example = "harness", required = true)
    private String name;

    @JsonProperty("description")
    @Schema(description = "Description of the agency", example = "A leading transportation company", required = true)
    private String description;

    @JsonProperty("capital")
    @Schema(description = "Capital of the agency", example = "1000000", required = true)
    private String capital;

    @JsonProperty("registreCommercial")
    @Schema(description = "registreCommercial of the agency", example = "1000000", required = true)
    private String registreCommercial;

    @JsonProperty("identifiantFiscal")
    @Schema(description = "Identifiant fiscal of the agency", example = "1234567890", required = true)
    private String identifiantFiscal;

    @JsonProperty("travauxPublic")
    @Schema(description = "Travaux publics of the agency", example = "Public works", required = true)
    private String travauxPublic;

    @JsonProperty("codeVille")
    @Schema(description = "Code of the city where the agency is located", example = "12345", required = true)
    private String codeVille;

    @JsonProperty("cnss")
    @Schema(description = "CNSS number of the agency", example = "1234567890", required = true)
    private String cnss;

    @JsonProperty("web")
    @Schema(description = "Website of the agency", example = "https://www.agency.com", required = true)
    private String web;

    @JsonProperty("rang")
    @Schema(description = "Rang of the agency", example = "1", required = true)
    private Integer rang;

    private AddressAgencyResponseDto mainAddress;
    
    private PhoneAgencyResponseDto mainPhone;
    private int sectorCount; 
}
