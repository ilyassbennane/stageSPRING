package com.ramadan.api.dto.costumer.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "CostumerRequest")
@Data
@NoArgsConstructor

public class CostumerRequestDto {

	@JsonProperty("adresseCostumer")
	@Schema(description = "Address of the Costumer", required = true)
	private List<AddressCostumerRequestDto> adressesCostumer;

	@JsonProperty("phoneCostumer")
	@Schema(description = "Phone of the Costumer", required = true)
	private List<PhoneCostumerRequestDto> phonesCostumer;

	@JsonProperty("emailCostumer")
	@Schema(description = "Email of the Costumer", required = true)
	private List<EmailCostumerRequestDto> emailsCostumer;

	@JsonProperty("paymentMethod")
	@Schema(description = "Method payment of the Costumer", required = true)
	@NotNull(message = "paymentMethod is required.")
	private List<PaymentMethodRequestDto> paymentMethods;

	@JsonProperty("familyCustomer_uuid")
	@Schema(name = "familyCustomer_uuid", type = "string", description = "family customer UUID associated with the costumer", example = "e8d5d9380f263bfcd46d51abc115323f6e7afaf18ecc5f8e722d6acd05311796")
	private String familyCustomerUuid;

	@JsonProperty("sector_uuid")
	@Schema(name = "sector_uuid", type = "string", description = "sector UUID associated with the costumer", example = "e8d5d9380f263bfcd46d51abc115323f6e7afaf18ecc5f8e722d6acd05311796")
	private String sectorUuid;

	@JsonProperty("name")
	@Schema(description = "name of the customer", example = "ilyass", required = true)
	@NotBlank(message = "name is required")
	private String name;

	@JsonProperty("raisonSociale1")
	@Schema(description = "Primary business name of the customer", example = "Company ABC", required = true)
	@NotBlank(message = "raisonSociale is required")
	private String raisonSociale1;

	@JsonProperty("raisonSociale2")
	@Schema(description = "Secondary business name of the customer", example = "Subsidiary XYZ", required = false)
	private String raisonSociale2;

	@JsonProperty("codeTva")
	@Schema(description = "VAT code of the customer", example = "TVA123456", required = true)
	@NotBlank(message = "codeTva is required")
	private String codeTva;

	@JsonProperty("codeGender")
	@Schema(description = "Gender code of the customer", example = "M", required = true)
	@NotBlank(message = "codeGender is required")
	private String codeGender;

	@JsonProperty("barcode")
	@Schema(description = "Barcode of the customer", example = "1234567890123", required = true)
	private String barcode;

	@JsonProperty("rang")
	@Schema(description = "Rank of the customer", example = "1", required = false)
	private Integer rang;

}
