package com.ramadan.api.dto.agance.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.ramadan.api.dto.address.model.AddressDto;
import com.ramadan.api.dto.agance.model.AddressAgencyRequestDto;
import com.ramadan.api.dto.agance.model.AddressAgencyResponseDto;
import com.ramadan.api.dto.agance.model.AgenceResponseDto;
import com.ramadan.api.dto.depot.relationDepositAgency.RelationDepositAgencyRequestDto;
import com.ramadan.api.dto.depot.relationDepositAgency.RelationDepositAgencyUpdateDto;
import com.ramadan.api.dto.secteur.model.SecteurResponseDto;
import com.ramadan.api.entity.address.Address;
import com.ramadan.api.entity.agence.AdresseAgency;
import com.ramadan.api.entity.agence.Agency;
import com.ramadan.api.entity.agence.Sector;

public interface IAddressAgencyMapperService {
  
    
AdresseAgency convertRequestSaveDtoToEntity(AddressAgencyRequestDto AdresseAgencyDto);

AdresseAgency convertDtoToEntity(AddressAgencyResponseDto oAdresseAgencyDto);

AddressAgencyResponseDto convertEntityToDto(AdresseAgency oAdresseAgency);
	
	List<AddressAgencyResponseDto> convertListToListDto(final Collection<AdresseAgency> entityList);
	
	 List<AdresseAgency> mapAddresses(List<AddressAgencyRequestDto> adresseAgencyDtos, Agency agence);


	Page<AddressAgencyResponseDto> convertToResponseDto(Page<AdresseAgency> addresses);
}
