package com.ramadan.api.dto.mission.service;

import org.springframework.data.domain.Page;

import com.ramadan.api.dto.mission.model.MissionResponseDto;
import com.ramadan.api.entity.tour.Tour;

public interface IMissionMapperService {
    Page<MissionResponseDto> convertToResponseDto(Page<Tour> missions);
}
