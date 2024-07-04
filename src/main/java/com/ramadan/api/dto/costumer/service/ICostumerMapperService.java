package com.ramadan.api.dto.costumer.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.ramadan.api.dto.costumer.model.CostumerDto;
import com.ramadan.api.dto.costumer.model.CostumerRequestDto;
import com.ramadan.api.dto.costumer.model.CostumerUpdateDto;
import com.ramadan.api.entity.costumer.Costumer;

public interface ICostumerMapperService {

	Page<CostumerDto> convertToResponseDto(Page<Costumer> costumers);

	Costumer convertRequestSaveDtoToEntity(CostumerRequestDto costumerDto);

	Costumer convertRequestUpdateDtoToEntity(CostumerUpdateDto costumerDto);

	Costumer convertDtoToEntity(CostumerDto costumerDto);

	CostumerDto convertEntityToDto(Costumer costumer);

	List<CostumerDto> convertListToListDto(Collection<Costumer> entityList);

}
