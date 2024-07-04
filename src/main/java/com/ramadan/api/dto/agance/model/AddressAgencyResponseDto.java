package com.ramadan.api.dto.agance.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramadan.api.dto.address.model.AddressDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "AddressAgencyResponseDto")
public class AddressAgencyResponseDto extends AddressDto {
	@JsonProperty("locationGPS")
	@Schema(name = "locationGPS", type = "Position", description = "GPS location of the address", required = true)
	private LocationGPSAgencyResponseDto locationGPSDto;



}
