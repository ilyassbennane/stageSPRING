package com.ramadan.api.dto.company.model;


import com.fasterxml.jackson.annotation.JsonProperty;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;



@Schema(
   name = "CompanyRequest"
)
@Data
@NoArgsConstructor
public class CompanyRequestDto {

   @JsonProperty("description")
   @Schema(
      description = "Description of the company",
      example = "A leading transportation company",
      required = true
   )
   @NotBlank(message = "Description is mandatory")
   private String description;

   @JsonProperty("prefix")
   @Schema(
      description = "Prefix of the company's phone number",
      example = "012",
      required = true
   )
   @NotBlank(message = "Prefix is mandatory")
   @Size(min = 1, max = 5, message = "Prefix must be between 1 and 5 characters")
   private String prefix;

   @JsonProperty("telephone")
   @Schema(
      description = "Telephone number of the company",
      example = "0123456789",
      required = true
   )
   @NotBlank(message = "Telephone is mandatory")
   @Pattern(regexp = "\\d{10}", message = "Telephone must be 10 digits")
   private String telephone;

   @JsonProperty("fax")
   @Schema(
      description = "Fax number of the company",
      example = "0123456789",
      required = true
   )
   @NotBlank(message = "Fax is mandatory")
   @Pattern(regexp = "\\d{10}", message = "Fax must be 10 digits")
   private String fax;

   @JsonProperty("pattente")
   @Schema(
      description = "Patent number of the company",
      example = "1234567890",
      required = true
   )
   @NotBlank(message = "Pattente is mandatory")
   @Pattern(regexp = "\\d+", message = "Pattente must be a number")
   private String pattente;

   @JsonProperty("capital")
   @Schema(
      description = "Capital of the company",
      example = "10000000",
      required = true
   )
   @NotBlank(message = "Capital is mandatory")
   @Pattern(regexp = "\\d+", message = "Capital must be a number")
   private String capital;

   @JsonProperty("registreCommercial")
   @Schema(
      description = "Commercial register number of the company",
      example = "1234567890",
      required = true
   )
   @NotBlank(message = "RegistreCommercial is mandatory")
   @Pattern(regexp = "\\d+", message = "RegistreCommercial must be a number")
   private String registreCommercial;

   @JsonProperty("identifiantFiscal")
   @Schema(
      description = "Identifiant fiscal number of the company",
      example = "1234567890",
      required = true
   )
   @NotBlank(message = "IdentifiantFiscal is mandatory")
   @Pattern(regexp = "\\d+", message = "IdentifiantFiscal must be a number")
   private String identifiantFiscal;

   @JsonProperty("travauxPublic")
   @Schema(
      description = "Travaux publics of the company",
      example = "Public works",
      required = true
   )
   @NotBlank(message = "TravauxPublic is mandatory")
   private String travauxPublic;

   @JsonProperty("cnss")
   @Schema(
      description = "CNSS number of the company",
      example = "1234567890",
      required = true
   )
   @NotBlank(message = "CNSS is mandatory")
   @Pattern(regexp = "\\d+", message = "CNSS must be a number")
   private String cnss;

   @JsonProperty("web")
   @Schema(
      description = "Website of the company",
      example = "https://www.company.com",
      required = true
   )
   @NotBlank(message = "Web is mandatory")
   private String web;

   @JsonProperty("codeVille")
   @Schema(
      description = "Code of the city where the company is located",
      example = "12345",
      required = true
   )
   @NotBlank(message = "CodeVille is mandatory")
   @Pattern(regexp = "\\d+", message = "CodeVille must be a number")
   private String codeVille;

   @JsonProperty("codeFormeJuridique")
   @Schema(
      description = "Code of the legal form of the company",
      example = "SARL",
      required = true
   )
   @NotBlank(message = "CodeFormeJuridique is mandatory")
   private String codeFormeJuridique;

   @JsonProperty("codeSecteurActivite")
   @Schema(
      description = "Code of the sector of activity of the company",
      example = "TRANSPORT",
      required = true
   )
   @NotBlank(message = "CodeSecteurActivite is mandatory")
   private String codeSecteurActivite;

   @JsonProperty("name")
   @Schema(
      description = "Name of the company",
      example = "Ramadan Transport",
      required = true
   )
   @NotBlank(message = "Name is mandatory")
   private String name;
}
