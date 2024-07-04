package com.ramadan.api.dto.agance.model;

import com.ramadan.api.dto.address.model.AddressDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "AddressAgencyResponseDto")
public class AddressAgencyResponseDto extends AddressDto {

}
