package com.ramadan.api.dto.agance.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ramadan.api.dto.agance.model.AddressAgencyResponseDto;
import com.ramadan.api.dto.agance.model.AgenceResponseDto;
import com.ramadan.api.dto.agance.model.PhoneAgencyResponseDto;
import com.ramadan.api.dto.depot.relationDepositAgency.RelationDepositAgencyRequestDto;
import com.ramadan.api.dto.depot.relationDepositAgency.RelationDepositAgencyUpdateDto;
import com.ramadan.api.entity.agence.AdresseAgency;
import com.ramadan.api.entity.agence.Agency;
import com.ramadan.api.entity.agence.PhoneAgency;
import com.ramadan.api.repository.agence.AdresseAgencyRepository;
import com.ramadan.api.repository.agence.PhoneAgenceRepository;
import com.ramadan.api.repository.agence.SecteurRepository;

@Service
public class AganceMapperService implements IAganceMapperService {

    private final ModelMapper modelMapper;
    
    private final AdresseAgencyRepository adresseAgencyRepository;
    private final PhoneAgenceRepository phoneAgencyRepository;
    private final AddressAgencyMapperService addressAgencyMapperService;
    private final PhoneAgencyMapperService phoneAgencyMapperService;
    @Autowired
    private SecteurRepository sectorRepository;


    @Autowired
    public AganceMapperService(ModelMapper modelMapper,PhoneAgencyMapperService phoneAgencyMapperService,AddressAgencyMapperService addressAgencyMapperService, AdresseAgencyRepository adresseAgencyRepository, PhoneAgenceRepository phoneAgencyRepository) {
        this.modelMapper = modelMapper;
        this.adresseAgencyRepository = adresseAgencyRepository;
        this.phoneAgencyRepository = phoneAgencyRepository;
        this.addressAgencyMapperService=addressAgencyMapperService;
        this.phoneAgencyMapperService=phoneAgencyMapperService;
        
    }

    @Override
    public Page<AgenceResponseDto> convertToResponseDto(Page<Agency> agences) {
        return agences.map(this::convertEntityToDto);
    }

    @Override
    public Agency convertRequestSaveDtoToEntity(RelationDepositAgencyRequestDto agencyDto) {
        return modelMapper.map(agencyDto, Agency.class);
    }

    @Override
    public Agency convertRequestUpdateDtoToEntity(RelationDepositAgencyUpdateDto agencyDto) {
        return modelMapper.map(agencyDto, Agency.class);
    }

    @Override
    public Agency convertDtoToEntity(AgenceResponseDto agencyDto) {
        return modelMapper.map(agencyDto, Agency.class);
    }

    @Override
    public AgenceResponseDto convertEntityToDto(Agency agency) {
        AgenceResponseDto dto = modelMapper.map(agency, AgenceResponseDto.class);
        
        // Find the main address and phone
        AdresseAgency mainAddress = adresseAgencyRepository.findByAgencyUuidAndIsMainTrue(agency.getUuid());
        PhoneAgency mainPhone = phoneAgencyRepository.findByAgencyUuidAndIsMainTrue(agency.getUuid());
        AddressAgencyResponseDto oMainAddressDto=addressAgencyMapperService.convertEntityToDto(mainAddress);
        PhoneAgencyResponseDto oMainPhoneDto=phoneAgencyMapperService.convertEntityToDto(mainPhone);
        dto.setMainAddress(oMainAddressDto);
        dto.setMainPhone(oMainPhoneDto);
        int sectorCount = sectorRepository.countByAgencyId(agency.getId());
        dto.setSectorCount(sectorCount);

        return dto;
    }
    @Override
    public List<AgenceResponseDto> convertListToListDto(Collection<Agency> entityList) {
        return entityList.stream()
                         .map(this::convertEntityToDto)
                         .collect(Collectors.toList());
    }
}
