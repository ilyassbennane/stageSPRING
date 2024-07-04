package com.ramadan.api.dto.address.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.ramadan.api.entity.address.Address}
 */
@Data
@NoArgsConstructor
public class AddressCompanyRequestDto extends AddressRequestDto {

	@JsonProperty("companyUuid")
	@Schema(description = "Uuid of the Company", example = "harness", required = true)
	String companyUuid;

}