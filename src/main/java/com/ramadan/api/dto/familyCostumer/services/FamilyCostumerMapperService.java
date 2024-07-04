package com.ramadan.api.dto.familyCostumer.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ramadan.api.dto.costumer.model.CostumerDto;
import com.ramadan.api.dto.familyCostumer.model.FamilyCostumerDto;
import com.ramadan.api.dto.familyCostumer.model.FamilyCostumerResponseDto;
import com.ramadan.api.entity.costumer.Costumer;
import com.ramadan.api.entity.costumer.FamilyCostumer;
@Service

public class FamilyCostumerMapperService implements IFamilyCostumerMapperService {

    private final ModelMapper modelMapper;

    public FamilyCostumerMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public FamilyCostumerResponseDto convertEntityToDto(FamilyCostumer familycostumer) {
        return modelMapper.map(familycostumer, FamilyCostumerResponseDto.class);
    }

    @Override
    public List<FamilyCostumerResponseDto> convertListToListDto(Collection<FamilyCostumer> entityList) {
        return entityList.stream()
                         .map(this::convertEntityToDto)
                         .collect(Collectors.toList());
    }
}
