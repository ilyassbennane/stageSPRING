package com.ramadan.api.dto.mission.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ramadan.api.dto.mission.model.MissionResponseDto;
import com.ramadan.api.entity.tour.Tour;

@Service

public class MissionMapperService implements IMissionMapperService{

    private final ModelMapper modelMapper;
    public MissionMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public Page<MissionResponseDto> convertToResponseDto(Page<Tour> missions) {
        return missions.map((element) -> modelMapper.map(element, MissionResponseDto.class));
    }
}
