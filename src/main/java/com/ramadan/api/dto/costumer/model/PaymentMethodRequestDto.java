package com.ramadan.api.dto.costumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "PaymentMethodRequest")
@Data
@NoArgsConstructor
public class PaymentMethodRequestDto {
	@JsonProperty("paymentMethodCostumer")
	@Schema(description = "payment method of the costumer", example = "cheque", required = true)
	private String payment_method;
	
}
