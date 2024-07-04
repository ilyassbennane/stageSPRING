package com.ramadan.api.dto.secteur.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ramadan.api.dto.costumer.model.CostumerDto;
import com.ramadan.api.dto.secteur.model.SecteurResponseDto;
import com.ramadan.api.entity.agence.Sector;
import com.ramadan.api.entity.costumer.Costumer;

@Service

public class SecteurMapperService implements ISecteurMapperService{


    private final ModelMapper modelMapper;
    public SecteurMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public Page<SecteurResponseDto> convertToResponseDto(Page<Sector> secteurs) {
        return secteurs.map((element) -> modelMapper.map(element,SecteurResponseDto.class));
    }
    
    @Override
    public SecteurResponseDto convertEntityToDto(Sector secteur) {
        return modelMapper.map(secteur, SecteurResponseDto.class);
    }
    
    @Override
    public List<SecteurResponseDto> convertListToListDto(Collection<Sector> entityList) {
        return entityList.stream()
                         .map(this::convertEntityToDto)
                         .collect(Collectors.toList());
    }
}