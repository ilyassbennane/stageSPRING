package com.ramadan.api.dto.costumer.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ramadan.api.dto.costumer.model.CostumerDto;
import com.ramadan.api.dto.costumer.model.CostumerRequestDto;
import com.ramadan.api.dto.costumer.model.CostumerUpdateDto;
import com.ramadan.api.entity.costumer.Costumer;

@Service
public class CostumerMapperService implements ICostumerMapperService {

    private final ModelMapper modelMapper;

    public CostumerMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<CostumerDto> convertToResponseDto(Page<Costumer> costumers) {
        return costumers.map(this::convertEntityToDto);
    }

    @Override
    public Costumer convertRequestSaveDtoToEntity(CostumerRequestDto costumerDto) {
        return modelMapper.map(costumerDto, Costumer.class);
    }

    @Override
    public Costumer convertRequestUpdateDtoToEntity(CostumerUpdateDto costumerDto) {
        return modelMapper.map(costumerDto, Costumer.class);
    }

    @Override
    public Costumer convertDtoToEntity(CostumerDto costumerDto) {
        return modelMapper.map(costumerDto, Costumer.class);
    }

    @Override
    public CostumerDto convertEntityToDto(Costumer costumer) {
        return modelMapper.map(costumer, CostumerDto.class);
    }

    @Override
    public List<CostumerDto> convertListToListDto(Collection<Costumer> entityList) {
        return entityList.stream()
                         .map(this::convertEntityToDto)
                         .collect(Collectors.toList());
    }
}
