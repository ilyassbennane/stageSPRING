package com.ramadan.api.dto.costumer.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.ramadan.api.dto.costumer.model.PhoneCostumerRequestDto;
import com.ramadan.api.dto.costumer.model.PhoneCostumerResponseDto;
import com.ramadan.api.entity.costumer.Costumer;
import com.ramadan.api.entity.costumer.PhoneCostumer;

public interface IPhoneCostumerMapperService {

	Page<PhoneCostumerResponseDto> convertToResponseDto(Page<PhoneCostumer> phones);

	PhoneCostumer convertRequestSaveDtoToEntity(PhoneCostumerRequestDto phoneCostumerDto);

	PhoneCostumer convertDtoToEntity(PhoneCostumerResponseDto phoneCostumerDto);

	PhoneCostumerResponseDto convertEntityToDto(PhoneCostumer phoneCostumer);

	List<PhoneCostumerResponseDto> convertListToListDto(Collection<PhoneCostumer> entityList);

	List<PhoneCostumer> mapPhones(List<PhoneCostumerRequestDto> phoneCostumerDtos, Costumer customer);

}
