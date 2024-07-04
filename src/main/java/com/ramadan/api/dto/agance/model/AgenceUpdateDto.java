package com.ramadan.api.dto.agance.model;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO for {@link com.ramadan.api.entity.agence.Agency}
 */
@Data
@Schema(name = "AgenceUpdate")
public class AgenceUpdateDto {

    @JsonProperty("uuid")
    @Schema(description = "UUID of the agency updated", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6")
    private String uuid;
 
    @JsonProperty("code")
    @Schema(description = "Code of the agency", example = "AG001", required = true)
    //@NotBlank(message = "Code is required.")
    private String code;

    @JsonProperty("description")
    @Schema(description = "Description of the agency", example = "A leading transportation agency", required = true)
    //@NotBlank(message = "Description is required.")
    private String description;
    
//	@JsonProperty("companyUuid")
//    @Schema(description = "Company UUID", example = "12345678-90ab-cdef-1234-567890abcdef", required = true)
//    private String companyUuid;
//
//    @JsonProperty("pattente")
//    @Schema(description = "Pattente number of the agency", example = "1234567890", required = true)
//    @NotBlank(message = "Pattente number is required.")
//    private String pattente;

    @JsonProperty("name")
    @Schema(description = "Name of the agency", example = "harness", required = true)
   // @NotBlank(message = "Name is required.")
    //@Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters.")
    private String name;

//    @JsonProperty("telephone")
//    @Schema(description = "Telephone number of the agency", example = "0123456789", required = true)
//    @Pattern(regexp = "^\\d{10}$", message = "Telephone number must be 10 digits.")
//    private String telephone;
//
//    @JsonProperty("fax")
//    @Schema(description = "Fax number of the agency", example = "0123456789", required = false)
//    @NotBlank(message = "Fax number is required.")
//    @Pattern(regexp = "^\\d{10}$", message = "Fax number must be 10 digits.")
//    private String fax;

    @JsonProperty("capital")
    @Schema(description = "Capital of the agency", example = "1000000", required = true)
   // @NotBlank(message = "Capital is required.")
    private String capital;

    @JsonProperty("registreCommercial")
    @Schema(description = "Commercial register number of the agency", example = "1000000", required = true)
   // @NotBlank(message = "Commercial register number is required.")
    private String registreCommercial;

    @JsonProperty("identifiantFiscal")
    @Schema(description = "Fiscal identifier of the agency", example = "1234567890", required = true)
   // @NotBlank(message = "Fiscal identifier is required.")
    private String identifiantFiscal;

    @JsonProperty("travauxPublic")
    @Schema(description = "Public works of the agency", example = "Public works", required = true)
   // @NotBlank(message = "Public works is required.")
    private String travauxPublic;

    @JsonProperty("codeVille")
    @Schema(description = "Code of the city where the agency is located", example = "12345", required = true)
    //@NotBlank(message = "codeVille is required.")
    private String codeVille;

    @JsonProperty("cnss")
    @Schema(description = "CNSS number of the agency", example = "1234567890", required = true)
   // @NotBlank(message = "CNSS number is required.")
    private String cnss;

    @JsonProperty("web")
    @Schema(description = "Website of the agency", example = "https://www.agency.com", required = true)
    //@NotBlank(message = "Website is required.")
   // @URL(message = "Website must be a valid URL.")
    private String web;

    @JsonProperty("rang")
    @Schema(description = "Rank of the agency", example = "1", required = true)
   // @NotNull(message = "Rank is required.")
    private Integer rang;
}
