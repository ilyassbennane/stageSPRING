package com.ramadan.api.dto.agance.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ramadan.api.dto.agance.model.AddressAgencyRequestDto;
import com.ramadan.api.dto.agance.model.AddressAgencyResponseDto;
import com.ramadan.api.dto.agance.model.LocationGPSAgencyResponseDto;
import com.ramadan.api.entity.agence.AdresseAgency;
import com.ramadan.api.entity.agence.Agency;

@Service
public class AddressAgencyMapperService implements IAddressAgencyMapperService {

    private final ModelMapper modelMapper;

    public AddressAgencyMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<AddressAgencyResponseDto> convertToResponseDto(Page<AdresseAgency> addresses) {
        return addresses.map(element -> {
            AddressAgencyResponseDto dto = modelMapper.map(element, AddressAgencyResponseDto.class);
            // Manually map locationGPS if needed
            if (element.getLocationGPS() != null) {
                dto.setLocationGPSDto(modelMapper.map(element.getLocationGPS(), LocationGPSAgencyResponseDto.class));
            }
            return dto;
        });
    }

    @Override
    public AdresseAgency convertRequestSaveDtoToEntity(AddressAgencyRequestDto AdresseAgencyDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(AdresseAgencyDto, AdresseAgency.class);
    }

    @Override
    public AdresseAgency convertDtoToEntity(AddressAgencyResponseDto oAdresseAgencyDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(oAdresseAgencyDto, AdresseAgency.class);
    }

    @Override
    public AddressAgencyResponseDto convertEntityToDto(AdresseAgency oAdresseAgency) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        AddressAgencyResponseDto dto = modelMapper.map(oAdresseAgency, AddressAgencyResponseDto.class);
        // Manually map locationGPS if needed
        if (oAdresseAgency.getLocationGPS() != null) {
            dto.setLocationGPSDto(modelMapper.map(oAdresseAgency.getLocationGPS(), LocationGPSAgencyResponseDto.class));
        }
        return dto;
    }

    @Override
    public List<AddressAgencyResponseDto> convertListToListDto(Collection<AdresseAgency> entityList) {
        return entityList == null ? Collections.emptyList() : entityList.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AdresseAgency> mapAddresses(List<AddressAgencyRequestDto> adresseAgencyDtos, Agency agence) {
        return adresseAgencyDtos.stream()
                .map(addressDto -> {
                    AdresseAgency address = new AdresseAgency();
                    address.setAgency(agence);
                    address.setCodeVille(addressDto.getCodeVille());
                    address.setAddress1(addressDto.getAddress1());
                    address.setAddress2(addressDto.getAddress2());
                    address.setAddress3(addressDto.getAddress3());
                    // address.setMain(addressDto.isMain());
                    return address;
                })
                .collect(Collectors.toList());
    }
}
