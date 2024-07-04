package com.ramadan.api.dto.address.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.ramadan.api.entity.address.Address}
 */
@Data
@NoArgsConstructor
public class AddressSupplierRequestDto extends AddressRequestDto {

    private String supplierUuid;

}