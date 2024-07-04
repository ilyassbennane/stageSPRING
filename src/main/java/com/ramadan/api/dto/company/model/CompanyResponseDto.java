// Source code is decompiled from a .class file using FernFlower decompiler.
package com.ramadan.api.dto.company.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
   name = "Company"
)
@Data
@NoArgsConstructor
public class CompanyResponseDto {
   @JsonProperty("uuid")
   @Schema(
      description = "uuid of the company ",
      example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6",
      required = true
   )
   String uuid;
   @JsonProperty("code")
   @Schema(
      description = "Code of the company",
      example = "COMP001",
      required = true
   )
   private String code;
   @JsonProperty("description")
   @Schema(
      description = "Description of the company",
      example = "A leading transportation company",
      required = true
   )
   private String description;
   @JsonProperty("prefix")
   @Schema(
      description = "Prefix of the company's phone number",
      example = "012",
      required = true
   )
   private String prefix;
   @JsonProperty("telephone")
   @Schema(
      description = "Telephone number of the company",
      example = "0123456789",
      required = true
   )
   private String telephone;
   @JsonProperty("fax")
   @Schema(
      description = "Fax number of the company",
      example = "0123456789",
      required = true
   )
   private String fax;
   @JsonProperty("pattente")
   @Schema(
      description = "Patent number of the company",
      example = "1234567890",
      required = true
   )
   private String pattente;
   @JsonProperty("capital")
   @Schema(
      description = "Capital of the company",
      example = "10000000",
      required = true
   )
   private String capital;
   @JsonProperty("registreCommercial")
   @Schema(
      description = "Commercial register number of the company",
      example = "1234567890",
      required = true
   )
   private String registreCommercial;
   @JsonProperty("identifiantFiscal")
   @Schema(
      description = "Identifiant fiscal number of the company",
      example = "1234567890",
      required = true
   )
   private String identifiantFiscal;
   @JsonProperty("travauxPublic")
   @Schema(
      description = "Travaux publics of the company",
      example = "Public works",
      required = true
   )
   private String travauxPublic;
   @JsonProperty("cnss")
   @Schema(
      description = "CNSS number of the company",
      example = "1234567890",
      required = true
   )
   private String cnss;
   @JsonProperty("web")
   @Schema(
      description = "Website of the company",
      example = "https://www.company.com",
      required = true
   )
   private String web;
   @JsonProperty("codeVille")
   @Schema(
      description = "Code of the city where the company is located",
      example = "12345",
      required = true
   )
   private String codeVille;
   @JsonProperty("codeFormeJuridique")
   @Schema(
      description = "Code of the legal form of the company",
      example = "SARL"
   )
   private String codeFormeJuridique;
   @JsonProperty("codeSecteurActivite")
   @Schema(
      description = "Code of the sector of activity of the company",
      example = "TRANSPORT",
      required = true
   )
   private String codeSecteurActivite;
   @JsonProperty("name")
   @Schema(
      description = "Name of the company",
      example = "Ramadan Transport",
      required = true
   )
   private String name;
}
