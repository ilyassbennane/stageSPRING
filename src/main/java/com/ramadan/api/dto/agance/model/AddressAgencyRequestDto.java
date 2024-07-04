package com.ramadan.api.dto.agance.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.ramadan.api.dto.address.model.AddressRequestDto;
import com.ramadan.api.entity.agence.LocationGPSAdresseAgency;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.ramadan.api.entity.address.Address}
 */
@Data
@NoArgsConstructor
public class AddressAgencyRequestDto extends AddressRequestDto{


//	
//	public String getAgencyUuid() {
//		return null;
//	}

//	@JsonProperty("agencyUuid")
//    @Schema(  name = "agencyUuid",
//	        type = "string",
//    		description = "Uuid of the agency", example = "harness", required = true)
//    private String agencyUuid;
}