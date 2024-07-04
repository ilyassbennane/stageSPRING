package com.ramadan.api.dto.costumer.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for {@link com.ramadan.api.entity.costumer.Costumer}
 */
@Data
@NoArgsConstructor
@Schema(name = "Costumer")
public class CostumerDto {

    @JsonProperty("uuid")
    @Schema(description = "UUID of the customer", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6")
    private String uuid;

    @JsonProperty("raisonSociale1")
    @Schema(description = "Primary business name of the customer", example = "Company ABC", required = true)
    private String raisonSociale1;

    @JsonProperty("raisonSociale2")
    @Schema(description = "Secondary business name of the customer", example = "Subsidiary XYZ", required = false)
    private String raisonSociale2;

	@JsonProperty("name")
	@Schema(description = "name of the customer", example = "ilyass", required = true)
	private String name;
	
    @JsonProperty("codeTva")
    @Schema(description = "VAT code of the customer", example = "TVA123456", required = true)
    private String codeTva;

    @JsonProperty("codeGender")
    @Schema(description = "Gender code of the customer", example = "M", required = false)
    private String codeGender;

    @JsonProperty("barcode")
    @Schema(description = "Barcode of the customer", example = "1234567890123", required = false)
    private String barcode;

    @JsonProperty("isFavorite")
    @Schema(description = "Indicates if the customer is a favorite", example = "true", required = false)
    private boolean isFavorite;

    @JsonProperty("rang")
    @Schema(description = "Rank of the customer", example = "1", required = false)
    private Integer rang;
    
    @JsonProperty("sector_uuid")
    @Schema(description = "UUID of the sector", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6")
    private String uuidSector;
    
//    @JsonProperty("familyCustomer_uuid")
//    @Schema(description = "UUID of the family customer", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6")
//    private String uuidfamilycustomer;
    
    
    


}
