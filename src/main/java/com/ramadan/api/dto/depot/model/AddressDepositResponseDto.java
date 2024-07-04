package com.ramadan.api.dto.depot.model;

import com.ramadan.api.dto.address.model.AddressDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Schema(name = "AddressDepositResponseDto")
public class AddressDepositResponseDto  extends AddressDto  {

}
