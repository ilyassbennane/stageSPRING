package com.ramadan.api.dto.familyCostumer.services;

import java.util.Collection;
import java.util.List;

import com.ramadan.api.dto.familyCostumer.model.FamilyCostumerResponseDto;
import com.ramadan.api.entity.costumer.FamilyCostumer;

public interface IFamilyCostumerMapperService {

	FamilyCostumerResponseDto convertEntityToDto(FamilyCostumer familycostumer);

	List<FamilyCostumerResponseDto> convertListToListDto(Collection<FamilyCostumer> entityList);

}
