package com.ramadan.api.dto.secteur.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.ramadan.api.dto.secteur.model.SecteurResponseDto;
import com.ramadan.api.entity.agence.Sector;

public interface ISecteurMapperService {
    Page<SecteurResponseDto> convertToResponseDto(Page<Sector> secteurs);


	SecteurResponseDto convertEntityToDto(Sector secteur);

	List<SecteurResponseDto> convertListToListDto(Collection<Sector> entityList);
}
