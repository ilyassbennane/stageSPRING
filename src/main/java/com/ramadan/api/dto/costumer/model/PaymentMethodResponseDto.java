package com.ramadan.api.dto.costumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "PaymentMethodResponse")
@Data
@NoArgsConstructor
public class PaymentMethodResponseDto {
	
	@JsonProperty("uuid")
	@Schema(name = "uuid", type = "string", description = "UUID of the payment method", example = "e823a3b9-24e6-4a15-9a2d-8e7b463e9dbd", required = true)
	String uuid;

	@JsonProperty("costumerUuid")
	@Schema(name = "customerUuid", type = "string", description = "Uuid of the costumer", example = "harness", required = true)
	private String costumerUuid;
	
	@JsonProperty("paymentMethodCostumer")
	@Schema(description = "payment method of the costumer", example = "cheque", required = true)
	private String payment_method;
	
}
