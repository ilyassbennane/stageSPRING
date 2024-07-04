package com.ramadan.api.services.secteur;

import java.util.List;
import java.util.Map;

import com.ramadan.api.dto.address.model.LocationGPSDto;
import com.ramadan.api.dto.address.model.LocationGPSRequestDto;
import com.ramadan.api.dto.address.model.LocationGPSSectorRequestDto;
import com.ramadan.api.dto.secteur.model.SecteurRequestDto;
import com.ramadan.api.dto.secteur.model.SecteurResponseDto;
import com.ramadan.api.dto.secteur.model.SecteurUpdateDto;
import com.ramadan.api.dto.secteur.model.SectorSearchFilterDto;
import com.ramadan.api.entity.agence.Sector;
import com.ramadan.api.exceptions.APIErrorException;
import com.ramadan.api.exceptions.ApiKeyException;

public interface ISecteurService {
    SecteurResponseDto findByUuid(String uuid) throws APIErrorException;

    SecteurResponseDto saveSecteur(SecteurRequestDto secteurRequestDto) throws APIErrorException;

    SecteurResponseDto updateEntity(String uuid, SecteurUpdateDto requestDto) throws ApiKeyException;

    void deleteSecteur(String uuid) throws APIErrorException;


   Map<String, Object> findAllByAgency(String uuid,int page, int size,String[] sort) throws APIErrorException;


Sector checkSectorUuid(String uuid) throws APIErrorException;

void addLocationGpsToSector(String sectorUuid, List<LocationGPSSectorRequestDto> locations) throws APIErrorException;

Map<String, Object> search(SectorSearchFilterDto searchRequestDto, int page, int size, String[] sort)
		throws APIErrorException;
}
