package com.ramadan.api.dto.supplier;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "SupplierUpdateDto")
public class SupplierUpdateDto {

    @JsonProperty("uuid")
    @Schema(
        name = "uuid",
        type = "long",
        description = "ID of the supplier",
        example = "1",
        required = true
    )
    private String uuid;

    @JsonProperty("codeTypeFournisseur")
    @Schema(
        name = "codeTypeFournisseur",
        type = "string",
        description = "Code type fournisseur",
        example = "ABC123",
        required = true
    )
    private String codeTypeFournisseur;

    @JsonProperty("barcode")
    @Schema(
        name = "barcode",
        type = "string",
        description = "Barcode",
        example = "1234567890",
        required = true
    )
    private String barcode;

    @JsonProperty("telephone")
    @Schema(
        name = "telephone",
        type = "string",
        description = "Telephone",
        example = "123456789",
        required = true
    )
    private String telephone;

    @JsonProperty("email")
    @Schema(
        name = "email",
        type = "string",
        description = "Email",
        example = "supplier@example.com",
        required = true
    )
    private String email;

    @JsonProperty("raisonSociale1")
    @Schema(
        name = "raisonSociale1",
        type = "string",
        description = "Raison Sociale 1",
        example = "Supplier 1",
        required = true
    )
    private String raisonSociale1;

    @JsonProperty("raisonSociale2")
    @Schema(
        name = "raisonSociale2",
        type = "string",
        description = "Raison Sociale 2",
        example = "Supplier 2",
        required = false
    )
    private String raisonSociale2;

    @JsonProperty("rang")
    @Schema(
        name = "rang",
        type = "integer",
        description = "Rang",
        example = "1",
        required = false
    )
    private Integer rang;

    // Getters and setters for the attributes
}
