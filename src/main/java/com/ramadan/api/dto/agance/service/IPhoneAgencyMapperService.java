package com.ramadan.api.dto.agance.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.ramadan.api.dto.agance.model.PhoneAgencyRequestDto;
import com.ramadan.api.dto.agance.model.PhoneAgencyResponseDto;
import com.ramadan.api.entity.agence.Agency;
import com.ramadan.api.entity.agence.PhoneAgency;

public interface IPhoneAgencyMapperService {

	Page<PhoneAgencyResponseDto> convertToResponseDto(Page<PhoneAgency> phones);

	PhoneAgency convertRequestSaveDtoToEntity(PhoneAgencyRequestDto phoneAgencyDto);

	PhoneAgency convertDtoToEntity(PhoneAgencyResponseDto phoneAgencyDto);

	PhoneAgencyResponseDto convertEntityToDto(PhoneAgency phoneAgency);

	List<PhoneAgencyResponseDto> convertListToListDto(Collection<PhoneAgency> entityList);

	List<PhoneAgency> mapPhones(List<PhoneAgencyRequestDto> phoneAgencyDtos, Agency agency);

}
