package com.ramadan.api.dto.agance.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.ramadan.api.dto.agance.model.AgenceResponseDto;
import com.ramadan.api.dto.depot.relationDepositAgency.RelationDepositAgencyRequestDto;
import com.ramadan.api.dto.depot.relationDepositAgency.RelationDepositAgencyUpdateDto;
import com.ramadan.api.entity.agence.Agency;


public interface IAganceMapperService {
    Page<AgenceResponseDto> convertToResponseDto(Page<Agency> agences);

Agency convertRequestSaveDtoToEntity(RelationDepositAgencyRequestDto AgencyDto);
	
	Agency convertRequestUpdateDtoToEntity(RelationDepositAgencyUpdateDto AgencyDto);

	Agency convertDtoToEntity(AgenceResponseDto oAgencyDto);

	AgenceResponseDto convertEntityToDto(Agency oAgency);
	
	List<AgenceResponseDto> convertListToListDto(final Collection<Agency> entityList);
    
}
