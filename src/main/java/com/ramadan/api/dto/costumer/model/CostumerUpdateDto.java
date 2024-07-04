package com.ramadan.api.dto.costumer.model;



import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
   name = "CostumerUpdate"
)
@Data
@NoArgsConstructor

public class CostumerUpdateDto {
	

    @JsonProperty("uuid")
    @Schema(description = "UUID of the costumer updated", example = "4615E16776D1G8G62DDG28377D23GV2D73RR382I6")
    private String uuid;
	@JsonProperty("name")
	@Schema(description = "name of the customer", example = "ilyass", required = true)
	//@NotBlank(message = "name is required")
	private String name;

	@JsonProperty("raisonSociale1")
	@Schema(description = "Primary business name of the customer", example = "Company ABC", required = true)
	//@NotBlank(message = "raisonSociale is required")
	private String raisonSociale1;

	@JsonProperty("raisonSociale2")
	@Schema(description = "Secondary business name of the customer", example = "Subsidiary XYZ", required = false)
	private String raisonSociale2;

	@JsonProperty("codeTva")
	@Schema(description = "VAT code of the customer", example = "TVA123456", required = true)
	//@NotBlank(message = "codeTva is required")
	private String codeTva;

	@JsonProperty("codeGender")
	@Schema(description = "Gender code of the customer", example = "M", required = true)
	//@NotBlank(message = "codeGender is required")
	private String codeGender;

	@JsonProperty("barcode")
	@Schema(description = "Barcode of the customer", example = "1234567890123", required = true)
	private String barcode;

	@JsonProperty("rang")
	@Schema(description = "Rank of the customer", example = "1", required = false)
	private Integer rang;
    @JsonProperty("sectorUuid")
    @Schema(description = "UUID of the sector", example = "some-sector-uuid", required = false)
    private String sectorUuid;
	
    @JsonProperty("familyCustomerUuid")
    @Schema(description = "UUID of the family customer", example = "some-family-customer-uuid", required = false)
    private String familyCustomerUuid;
}
