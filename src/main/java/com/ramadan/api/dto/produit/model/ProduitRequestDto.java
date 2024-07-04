
package com.ramadan.api.dto.produit.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "ProduitRequest")
public class ProduitRequestDto {


    @JsonProperty("description")
    @Schema(description = "Description of the product", example = "A leading transportation company")
    private String description;

    @JsonProperty("name")
    @Schema(description = "Name of the product", example = "Electronic Device")
    private String name;

    @JsonProperty("brand_uuid")
    @Schema(description = "UUID of the brand associated with the product", example = "12345678-90ab-cdef-1234-567890abcdef")
    private String brandUuid;

    @JsonProperty("categorie_uuid")
    @Schema(description = "UUID of the category associated with the product", example = "12345678-90ab-cdef-1234-567890abcdef")
    private String categorieUuid;

    @JsonProperty("codebare")
    @Schema(description = "Codebare of the product", example = "1234567890123")
    private String codebare;

    @JsonProperty("prixVente")
    @Schema(description = "Selling price of the product", example = "100.00")
    private Double prixVente;

    @JsonProperty("prixAchat")
    @Schema(description = "Purchase price of the product", example = "50.00")
    private Double prixAchat;

    @JsonProperty("prixRetour")
    @Schema(description = "Return price of the product", example = "70.00")
    private Double prixRetour;

    @JsonProperty("codeUnit")
    @Schema(description = "Unit code of the product", example = "EA")
    private String codeUnit;

    @JsonProperty("rang")
    @Schema(description = "Rank of the product", example = "1")
    private Integer rang;

    @JsonProperty("nombreJoursExpiration")
    @Schema(description = "Number of days for expiration", example = "30")
    private Integer nombreJoursExpiration;
}
